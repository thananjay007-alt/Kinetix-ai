package com.example.data.exporter

import com.example.data.api.GeminiBiomechanicalService

object PythonScriptExporter {

    fun generatePythonScript(videoFileName: String = "athlete_movement.mp4"): String {
        return """
# ==============================================================================
# Biomechanical Kinematic Analyzer - Python Backend
# Uses Google Generative AI SDK with Video File API & Biomechanical System Prompt
# ==============================================================================
# Requirements:
#   pip install google-generativeai
#
# Usage:
#   export GEMINI_API_KEY="your_api_key_here"
#   python analyze_movement.py $videoFileName
# ==============================================================================

import os
import sys
import time
import google.generativeai as genai

# 1. Configure Gemini API Key
api_key = os.environ.get("GEMINI_API_KEY")
if not api_key:
    raise ValueError("GEMINI_API_KEY environment variable is missing. Please set it before running.")

genai.configure(api_key=api_key)

# 2. System Instructions for Elite Biomechanical Form Analysis
SYSTEM_INSTRUCTIONS = ${"\"\"\""}${GeminiBiomechanicalService.BIOMECHANICAL_SYSTEM_INSTRUCTION}${"\"\"\""}

def analyze_athlete_video(video_path: str):
    if not os.path.exists(video_path):
        print(f"Error: Video file '{video_path}' not found.")
        sys.exit(1)

    print(f"[1/4] Uploading video '{video_path}' to Google Gemini Files API...")
    video_file = genai.upload_file(path=video_path)
    print(f"      File uploaded successfully. Remote URI: {video_file.name}")

    print("[2/4] Waiting for video processing to complete...")
    while video_file.state.name == "PROCESSING":
        time.sleep(2)
        video_file = genai.get_file(video_file.name)

    if video_file.state.name == "FAILED":
        raise ValueError(f"Video processing failed on Gemini server.")

    print(f"[3/4] Video is ready (State: {video_file.state.name}). Initializing Biomechanical Model...")

    # 3. Initialize Model with Strict Temperature 0.2 and System Prompt
    # Gemini 1.5 Pro / Gemini Pro handles deep multimodal video reasoning
    model = genai.GenerativeModel(
        model_name="gemini-1.5-pro",
        system_instruction=SYSTEM_INSTRUCTIONS,
        generation_config={
            "temperature": 0.2,  # Strict temperature prevents hallucinations
            "top_p": 0.95,
            "top_k": 40
        }
    )

    print("[4/4] Generating kinematic breakdown ('Analyze this form')...")
    response = model.generate_content([
        video_file,
        "Analyze this form."
    ])

    print("\n" + "=" * 60)
    print("      BIOMECHANICAL KINEMATIC ANALYSIS REPORT")
    print("=" * 60 + "\n")
    print(response.text)
    print("\n" + "=" * 60)

    # Clean up uploaded file from Gemini server storage
    print("Cleaning up remote video file...")
    genai.delete_file(video_file.name)
    print("Done.")

if __name__ == "__main__":
    target_video = sys.argv[1] if len(sys.argv) > 1 else "$videoFileName"
    analyze_athlete_video(target_video)
""".trimIndent()
    }

    fun getGoogleAIStudioInstructions(): String {
        return """
How to run in Google AI Studio (Web UI):
1. Open https://aistudio.google.com/ and click "New Prompt" -> "System Instructions"
2. Paste the Biomechanical System Instructions into the System Instructions field.
3. In Model Parameters (right panel):
   - Model: Gemini 1.5 Pro or Gemini Pro
   - Temperature: 0.2
4. In the User Prompt area:
   - Click the (+) icon -> Upload MP4 video
   - Type: "Analyze this form"
   - Click "Run" (Ctrl+Enter)
""".trimIndent()
    }
}
