package com.example.data.api

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Base64
import com.example.BuildConfig
import com.example.data.model.BiomechanicalReport
import com.example.data.parser.ReportParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class GeminiBiomechanicalService {

    companion object {
        const val PRIMARY_PRO_MODEL = "gemini-3.1-pro-preview"
        const val FALLBACK_FLASH_MODEL = "gemini-3.5-flash"

        const val BIOMECHANICAL_SYSTEM_INSTRUCTION = """You are an elite sports scientist and biomechanical engineer. Your task is to analyze the provided video of an athlete's movement and provide a highly detailed kinematic breakdown.
Whether the video shows a heavy weightlifting compound movement, a badminton jump smash, a cricket bowling action, or a shadow boxing combination, you must apply strict biomechanical principles to your analysis.
Analyze the video and output your response using the following strict structure:
1. Movement Identification
State the specific exercise or athletic movement being performed.
2. Phase-by-Phase Breakdown
Break the movement down into its distinct kinetic phases (e.g., Preparation/Setup, Execution/Concentric, Follow-through/Eccentric) and describe the athlete's mechanics in each phase.
3. Kinematic Analysis
Evaluate the critical biomechanical factors, including:
 * Joint angles and mobility
 * Spinal alignment and core stability
 * Footwork, base of support, and weight distribution
 * Kinetic chain efficiency (how well force is transferred)
4. Injury Risk Assessment
Flag any mechanical flaws that could lead to injury (e.g., lower back rounding, knee valgus, elbow flaring, over-rotation). If the form is safe, state "No critical injury risks detected."
5. Corrective Cues
Provide 2 to 3 actionable, specific coaching cues the athlete can use to immediately correct their form and optimize their performance.
Keep the tone professional, objective, and scientific. Use Markdown formatting to ensure the output is clean and readable."""
    }

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun analyzeMovementVideo(
        context: Context,
        videoUri: Uri,
        movementContextNote: String = "",
        modelName: String = PRIMARY_PRO_MODEL
    ): Result<BiomechanicalReport> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext Result.failure(
                IllegalStateException("GEMINI_API_KEY is not set. Please configure your key in the AI Studio Secrets panel.")
            )
        }

        try {
            // Extract key kinematic frames from the video to send to Gemini
            val frames = extractKinematicFrames(context, videoUri, maxFrames = 4)
            if (frames.isEmpty()) {
                return@withContext Result.failure(IllegalStateException("Could not read frames from the selected video file."))
            }

            val partsArray = JSONArray()
            val promptText = if (movementContextNote.isNotBlank()) {
                "Analyze this form. Context: $movementContextNote"
            } else {
                "Analyze this form."
            }
            partsArray.put(JSONObject().put("text", promptText))

            // Add kinematic image frames
            for (bitmap in frames) {
                val base64 = bitmapToBase64(bitmap)
                val inlineData = JSONObject()
                    .put("mimeType", "image/jpeg")
                    .put("data", base64)
                partsArray.put(JSONObject().put("inlineData", inlineData))
            }

            val requestJson = buildGeminiRequestJson(partsArray)

            val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=$apiKey"
            val requestBody = requestJson.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody)
                .build()

            val response = httpClient.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                // If Pro model quota or availability issue occurs, retry with Flash
                if (modelName == PRIMARY_PRO_MODEL) {
                    return@withContext analyzeMovementVideo(context, videoUri, movementContextNote, FALLBACK_FLASH_MODEL)
                }
                return@withContext Result.failure(
                    Exception("Gemini API error (${response.code}): $responseBody")
                )
            }

            val rawMarkdown = extractMarkdownFromResponse(responseBody)
            val parsedReport = ReportParser.parseMarkdownReport(rawMarkdown)
            Result.success(parsedReport)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun analyzePresetOrText(
        movementDescription: String,
        modelName: String = PRIMARY_PRO_MODEL
    ): Result<BiomechanicalReport> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext Result.failure(
                IllegalStateException("GEMINI_API_KEY is not configured in Secrets panel.")
            )
        }

        try {
            val partsArray = JSONArray()
            partsArray.put(JSONObject().put("text", "Analyze this form: $movementDescription"))

            val requestJson = buildGeminiRequestJson(partsArray)
            val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=$apiKey"
            val requestBody = requestJson.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody)
                .build()

            val response = httpClient.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("Gemini API error (${response.code}): $responseBody"))
            }

            val rawMarkdown = extractMarkdownFromResponse(responseBody)
            val parsedReport = ReportParser.parseMarkdownReport(rawMarkdown)
            Result.success(parsedReport)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun buildGeminiRequestJson(parts: JSONArray): JSONObject {
        val root = JSONObject()

        // System instruction
        val sysInstruction = JSONObject().put(
            "parts",
            JSONArray().put(JSONObject().put("text", BIOMECHANICAL_SYSTEM_INSTRUCTION))
        )
        root.put("systemInstruction", sysInstruction)

        // Contents
        val content = JSONObject().put("parts", parts)
        root.put("contents", JSONArray().put(content))

        // Generation config with strict temperature 0.2
        val genConfig = JSONObject()
            .put("temperature", 0.2)
            .put("topP", 0.95)
            .put("topK", 40)
        root.put("generationConfig", genConfig)

        return root
    }

    private fun extractMarkdownFromResponse(jsonStr: String): String {
        val jsonObj = JSONObject(jsonStr)
        val candidates = jsonObj.optJSONArray("candidates") ?: return "No response generated."
        if (candidates.length() == 0) return "No response generated."
        val firstCandidate = candidates.getJSONObject(0)
        val content = firstCandidate.optJSONObject("content") ?: return "No content found."
        val parts = content.optJSONArray("parts") ?: return "No parts found."
        val sb = StringBuilder()
        for (i in 0 until parts.length()) {
            val part = parts.getJSONObject(i)
            sb.append(part.optString("text", ""))
        }
        return sb.toString().ifBlank { "No text returned." }
    }

    private fun extractKinematicFrames(context: Context, videoUri: Uri, maxFrames: Int = 4): List<Bitmap> {
        val bitmaps = mutableListOf<Bitmap>()
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(context, videoUri)
            val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val durationMs = durationStr?.toLongOrNull() ?: 3000L

            val step = durationMs / (maxFrames + 1)
            for (i in 1..maxFrames) {
                val timeUs = (step * i) * 1000L
                val frame = retriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                if (frame != null) {
                    // Scale down frame to keep payload fast and high quality
                    val scaled = Bitmap.createScaledBitmap(frame, 640, 360, true)
                    bitmaps.add(scaled)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                retriever.release()
            } catch (_: Exception) {}
        }
        return bitmaps
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
        val bytes = baos.toByteArray()
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }
}
