package com.example

import com.example.data.parser.ReportParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ReportParserTest {

    private val sampleMarkdown = """
# 1. Movement Identification
Barbell Back Squat (Low-Bar Compound Movement)

# 2. Phase-by-Phase Breakdown
* **Setup & Unrack (0.0s - 1.2s)**: Athlete establishes stable bilateral base, feet 1.1x shoulder width, 25° outward foot flare.
* **Eccentric Descent (1.2s - 2.5s)**: Controlled descent with coordinated hip hinge and knee flexion.
* **Amortization (2.5s - 2.8s)**: Smooth reversal at parallel. Slight bilateral knee cave observed upon initiating drive.
* **Concentric Drive (2.8s - 3.8s)**: Rapid force transfer through midfoot, gluteal contraction.

# 3. Kinematic Analysis
* **Joint angles and mobility**: Hip flexion reaches 115°, knee flexion 122°, ankle dorsiflexion 28°.
* **Spinal alignment and core stability**: Spinal column remains locked in neutral lordosis without dynamic flexion.
* **Footwork, base of support, and weight distribution**: Ground reaction force vector passes directly over midfoot.
* **Kinetic chain efficiency**: 88% kinetic chain efficiency with clean vertical bar path.

# 4. Injury Risk Assessment
* **Mechanical flaw identified**: Mild transient knee valgus on ascent under load.
* Monitor adductor-to-abductor force balance to protect medial collateral ligament.

# 5. Corrective Cues
1. Screw feet into the floor: Externally rotate hips before initiating descent.
2. Spread the floor on ascent: Actively push knees out over 2nd/3rd toe tracking.
3. Pull the barbell into your traps: Maintain continuous latissimus tension to lock the kinetic chain.
""".trimIndent()

    @Test
    fun testReportParsing() {
        val report = ReportParser.parseMarkdownReport(sampleMarkdown, "Weightlifting")

        assertEquals("Barbell Back Squat (Low-Bar Compound Movement)", report.movementName)
        assertEquals("Weightlifting", report.sportCategory)
        assertTrue(report.phases.isNotEmpty())
        assertNotNull(report.kinematics.jointAngles)
        assertNotNull(report.kinematics.spinalAlignment)
        assertEquals(3, report.cues.size)
    }
}
