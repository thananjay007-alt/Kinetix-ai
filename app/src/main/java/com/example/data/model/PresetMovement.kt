package com.example.data.model

data class PresetMovement(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val iconName: String, // e.g. "fitness", "sports_tennis", "cricket", "sports_mma", "speed"
    val durationSeconds: String,
    val description: String,
    val keyJointsAnalyzed: List<String>,
    val sampleReport: BiomechanicalReport
)

object MovementPresets {
    val presets: List<PresetMovement> = listOf(
        PresetMovement(
            id = "barbell_squat",
            title = "Heavy Barbell Back Squat",
            subtitle = "Compound Lower Body Kinematic Chain",
            category = "Weightlifting",
            iconName = "fitness",
            durationSeconds = "00:04",
            description = "High-load bilateral squat examining hip/knee simultaneous extension, lumbar neutrality, and heel tripod stability.",
            keyJointsAnalyzed = listOf("Femoroacetabular (Hip)", "Tibiofemoral (Knee)", "Talocrural (Ankle)", "Lumbar Spine"),
            sampleReport = BiomechanicalReport(
                movementName = "Barbell Back Squat (Low-Bar Compound)",
                sportCategory = "Strength & Conditioning / Powerlifting",
                phases = listOf(
                    KineticPhase("1. Setup & Unrack", "0.0s - 1.2s", "Feet placed slightly wider than shoulder-width, toes angled 25° outward. Latissimus dorsi braced into ribcage, scapulae retracted creating solid rear-delt shelf."),
                    KineticPhase("2. Eccentric Descent", "1.2s - 2.5s", "Simultaneous hip break and knee flexion. Hips travel back and down while knees track in line with 2nd and 3rd toes. Torso maintains constant 55° forward tilt."),
                    KineticPhase("3. Inflection Point (Amortization)", "2.5s - 2.8s", "Femur reaches parallel (110° knee flexion). Minor 3° internal rotation at left knee (valgus twitch) as bottom turnaround occurs."),
                    KineticPhase("4. Concentric Drive & Lockout", "2.8s - 3.8s", "Powerful ground reaction force through midfoot tripod. Quadriceps drive knee extension followed by gluteus maximus hip thrust to neutral standing lockout.")
                ),
                kinematics = KinematicAnalysis(
                    jointAngles = "Hip Flexion: 115° at bottom turnaround | Knee Flexion: 122° | Ankle Dorsiflexion: 28° | Torso Inclination: 54° relative to vertical.",
                    spinalAlignment = "Excellent lumbar neutral maintained through descent. Minor loss of thoracic extension at bottom reversal.",
                    baseOfSupport = "Tripod foot contact (calcaneus, 1st metatarsal, 5th metatarsal) maintained with 60% load on heel-midfoot.",
                    kineticChainEfficiency = "88% transfer efficiency. Ground reaction force vector well-aligned with barbell center of mass.",
                    efficiencyScore = 88
                ),
                injuryAssessment = InjuryRiskAssessment(
                    riskLevel = RiskSeverity.LOW,
                    isSafe = false,
                    detectedFlaws = listOf("Transient Left Knee Valgus (mild internal collapse under load)"),
                    detailedSummary = "Mechanical integrity is predominantly robust. Mild left knee valgus twitch detected upon initiation of concentric drive indicates weak abductor recruitment under submaximal threshold."
                ),
                cues = listOf(
                    CorrectiveCue(1, "Spread the floor with the outside blades of your shoes", "Gluteus Medius / Knee Alignment", "Engage hip abductors dynamically through turnaround"),
                    CorrectiveCue(2, "Screw your feet into the ground to create outward torque", "Lower Kinetic Chain", "Improves external rotation torque at hip joint"),
                    CorrectiveCue(3, "Pull the bar down into your traps to stiffen your spinal cylinder", "Thoracolumbar Fascia", "Maximizes intra-abdominal pressure")
                ),
                rawMarkdown = """
# 1. Movement Identification
Barbell Back Squat (Low-Bar Compound Movement)

# 2. Phase-by-Phase Breakdown
* **Setup & Unrack (0.0s - 1.2s)**: Athlete establishes stable bilateral base, feet 1.1x shoulder width, 25° outward foot flare. High intra-abdominal pressure created via Valsalva maneuver.
* **Eccentric Descent (1.2s - 2.5s)**: Controlled descent with coordinated hip hinge and knee flexion. Knee tracking aligns with foot axes.
* **Amortization (2.5s - 2.8s)**: Smooth reversal at parallel. Slight bilateral knee cave observed upon initiating drive.
* **Concentric Drive (2.8s - 3.8s)**: Rapid force transfer through midfoot, gluteal contraction leading to full hip/knee extension.

# 3. Kinematic Analysis
* **Joint angles and mobility**: Hip flexion reaches 115°, knee flexion 122°, ankle dorsiflexion 28°. Ankle mobility is sufficient to prevent premature forward heel elevation.
* **Spinal alignment and core stability**: Spinal column remains locked in neutral lordosis without dynamic flexion or extension under load.
* **Footwork and base of support**: Ground reaction force vector passes directly over midfoot.
* **Kinetic chain efficiency**: 88% kinetic chain efficiency with clean vertical bar path.

# 4. Injury Risk Assessment
* **Mechanical flaw identified**: Mild transient knee valgus on ascent under load.
* **Risk status**: Low injury risk; monitor adductor-to-abductor force balance to protect medial collateral ligament and patellofemoral tracking.

# 5. Corrective Cues
1. *Screw feet into the floor*: Externally rotate hips before initiating descent.
2. *Spread the floor on ascent*: Actively push knees out over 2nd/3rd toe tracking.
3. *Pull the barbell into your traps*: Maintain continuous latissimus tension to lock the kinetic chain.
""".trimIndent()
            )
        ),
        PresetMovement(
            id = "badminton_jump_smash",
            title = "Badminton Overhead Jump Smash",
            subtitle = "High-Velocity Overhead Racket Kinetic Chain",
            category = "Racket Sports",
            iconName = "sports_tennis",
            durationSeconds = "00:02",
            description = "Explosive vertical jump with thoracic rotation, shoulder internal rotation whip, and pronation at impact.",
            keyJointsAnalyzed = listOf("Glenohumeral (Shoulder)", "Thoracic Spine", "Radiohumeral (Elbow)", "Knee Landing Mechanics"),
            sampleReport = BiomechanicalReport(
                movementName = "Badminton Overhead Jump Smash",
                sportCategory = "Racket Sports / Badminton",
                phases = listOf(
                    KineticPhase("1. Approach & Scissor Jump", "0.0s - 0.6s", "Rapid backward transition into explosive single-leg takeoff. Non-racket arm points to shuttlecock for spatial calibration."),
                    KineticPhase("2. Cocking & External Rotation", "0.6s - 1.1s", "Thoracic hyperextension with 90° shoulder abduction and extreme external rotation creating maximal stretch-shortening cycle in pectoralis and subscapularis."),
                    KineticPhase("3. Acceleration & Pronation", "1.1s - 1.4s", "Pelvis rotates first, followed by torso, shoulder internal rotation, elbow extension, and rapid forearm pronation prior to shuttlecock impact."),
                    KineticPhase("4. Follow-Through & Deceleration Landing", "1.4s - 2.0s", "Racket crosses torso diagonally while athlete lands softly on non-dominant foot with active knee flexion buffering impact forces.")
                ),
                kinematics = KinematicAnalysis(
                    jointAngles = "Shoulder External Rotation: 145° | Elbow Flexion at cocking: 88° | Thoracic Rotation: 42° | Knee Landing Flexion: 48°.",
                    spinalAlignment = "Dynamic S-curve with fluid thoracic extension during backswing. Minimal excessive lumbar hyperextension.",
                    baseOfSupport = "Airborne phase exhibits high rotational stability; landing base of support absorbs ground impact smoothly.",
                    kineticChainEfficiency = "94% force transfer. Proximal-to-distal sequencing is near-optimal from legs to core to racket tip.",
                    efficiencyScore = 94
                ),
                injuryAssessment = InjuryRiskAssessment(
                    riskLevel = RiskSeverity.SAFE,
                    isSafe = true,
                    detectedFlaws = emptyList(),
                    detailedSummary = "No critical injury risks detected. Dynamic shoulder stability and scapular control are excellent. Landing shock absorption protects patellar tendon."
                ),
                cues = listOf(
                    CorrectiveCue(1, "Lead with the elbow before whipping the forearm", "Upper Kinetic Chain", "Increases whip velocity and reduces anterior shoulder strain"),
                    CorrectiveCue(2, "Rotate non-racket arm inward to lock the ribcage at impact", "Thoracic Counter-Rotation", "Increases rotational core torque"),
                    CorrectiveCue(3, "Land softly onto midfoot with toe-to-heel roll", "Landing Mechanics", "Buffers 5-7x bodyweight ground reaction force")
                ),
                rawMarkdown = """
# 1. Movement Identification
Badminton Overhead Jump Smash (High-velocity racket kinematic chain)

# 2. Phase-by-Phase Breakdown
* **Approach & Scissor Jump (0.0s - 0.6s)**: Dynamic center of mass elevation via stretch-shortening cycle.
* **Cocking (0.6s - 1.1s)**: Thoracic extension, glenohumeral 90° abduction with 145° maximal external rotation.
* **Acceleration (1.1s - 1.4s)**: Flawless proximal-to-distal kinetic sequencing (hip -> trunk -> shoulder -> elbow -> wrist).
* **Follow-through & Landing (1.4s - 2.0s)**: High eccentric control decelerating racket head; landing on forefoot with active knee flexion.

# 3. Kinematic Analysis
* **Joint angles and mobility**: Scapular upward rotation allows safe subacromial clearance throughout terminal arm elevation.
* **Spinal alignment and core stability**: Dynamic core stiffness prevents lumbar shear during explosive rotation.
* **Footwork and base of support**: Centered landing maintains immediate readiness for return split-step.
* **Kinetic chain efficiency**: 94% kinetic chain transfer with elite angular velocities (>1800°/s forearm pronation).

# 4. Injury Risk Assessment
No critical injury risks detected. Form demonstrates outstanding protective scapulohumeral rhythm.

# 5. Corrective Cues
1. *Lead with the elbow cap*: Initiate forward arm acceleration with elbow pointed forward before uncoiling the forearm.
2. *Snap non-dominant elbow to ribs*: Decelerate torso rotation to maximize racket head whip.
""".trimIndent()
            )
        ),
        PresetMovement(
            id = "cricket_fast_bowling",
            title = "Cricket Fast Bowling Action",
            subtitle = "Front-Foot Brace & Trunk Flexion Mechanics",
            category = "Cricket",
            iconName = "cricket",
            durationSeconds = "00:03",
            description = "Run-up gather, back-foot contact, front-foot brace with stiff knee, and explosive lateral trunk flexion.",
            keyJointsAnalyzed = listOf("Lead Knee (Brace)", "Thoracolumbar Spine", "Bowling Shoulder", "Pelvis-Shoulder Separation"),
            sampleReport = BiomechanicalReport(
                movementName = "Cricket Fast Bowling (Right-Arm Pace)",
                sportCategory = "Cricket / Fast Bowling",
                phases = listOf(
                    KineticPhase("1. Bound & Back-Foot Contact (BFC)", "0.0s - 0.8s", "Athlete plants back foot perpendicular to crease. Shoulder counter-rotation creates 32° separation angle between pelvis and shoulders."),
                    KineticPhase("2. Front-Foot Contact (FFC) & Brace", "0.8s - 1.4s", "Lead leg strikes ground with locked knee (175° extension), acting as mechanical pivot hurdle to catapult upper body forward."),
                    KineticPhase("3. Ball Release & Bowling Arm Extension", "1.4s - 1.7s", "Trunk flexes forward 45° over braced front leg. Bowling arm remains straight (<10° flexion) per ICC biomechanical regulations."),
                    KineticPhase("4. Follow-Through Deceleration", "1.7s - 2.8s", "Athlete runs through diagonal crease vector, absorbing deceleration forces across 3-4 decelerating strides.")
                ),
                kinematics = KinematicAnalysis(
                    jointAngles = "Front Knee Angle at FFC: 172° (excellent brace) | Shoulder-Hip Separation: 34° | Lateral Trunk Flexion: 26°.",
                    spinalAlignment = "High lateral flexion coupled with rotation detected during release. Lumbar hyperextension noted at BFC.",
                    baseOfSupport = "Narrow base with high braking ground reaction forces (up to 8x BW). Strong front heel anchor.",
                    kineticChainEfficiency = "91% linear to rotational conversion. Front foot brace halts momentum and levers bowling shoulder forward.",
                    efficiencyScore = 91
                ),
                injuryAssessment = InjuryRiskAssessment(
                    riskLevel = RiskSeverity.MODERATE,
                    isSafe = false,
                    detectedFlaws = listOf("Excessive Lateral Trunk Flexion (>25°)", "Mixed Action Counter-Rotation (risk of L4-L5 lumbar pars stress)"),
                    detailedSummary = "Mixed bowling action flaw detected: Hips are semi-open while shoulders counter-rotate into side-on alignment. Combined with 26° lateral flexion, this puts high shear strain on contralateral lumbar pars interarticularis."
                ),
                cues = listOf(
                    CorrectiveCue(1, "Align hips and shoulders into pure front-on or pure side-on", "Spinal Safety / Mixed Action", "Eliminates lumbar torsion during back-foot impact"),
                    CorrectiveCue(2, "Keep non-bowling arm tight and pull down towards hip", "Rotational Vector", "Prevents excessive lateral spine bending"),
                    CorrectiveCue(3, "Drive chest straight over the braced front knee", "Trunk Flexion", "Converts energy into forward velocity rather than spinal shear")
                ),
                rawMarkdown = """
# 1. Movement Identification
Cricket Fast Bowling Action (Right-arm medium-fast delivery)

# 2. Phase-by-Phase Breakdown
* **Back-Foot Contact (0.0s - 0.8s)**: Impact angle 85° to crease line. Torso exhibits counter-rotation relative to pelvic angle.
* **Front-Foot Contact (0.8s - 1.4s)**: Superior knee brace mechanism with minimal knee flex (<8° collapse).
* **Delivery Stride & Release (1.4s - 1.7s)**: High release point. Non-bowling arm pull assists trunk flexion.
* **Follow-through (1.7s - 2.8s)**: Smooth trajectory through the off-side crease.

# 3. Kinematic Analysis
* **Joint angles and mobility**: Lead knee maintains rigid 172° pillar, creating maximum catapult lever arm.
* **Spinal alignment and core stability**: 26° lateral trunk flexion coupled with spinal twisting generates high asymmetric shear across L4-L5 vertebrae.
* **Footwork and base of support**: Ground reaction force spike absorbed effectively by footwear and braced limb.
* **Kinetic chain efficiency**: 91% conversion efficiency into ball release speed (~138 km/h estimated).

# 4. Injury Risk Assessment
* **Mechanical flaw identified**: Mixed action counter-rotation (>30° separation between pelvis and upper torso with high lateral flexion).
* **Risk status**: Moderate-to-High lumbar stress fracture risk (pars interarticularis injury profile).

# 5. Corrective Cues
1. *Unify hip and shoulder orientation*: Avoid twisting shoulders back if hips are already facing target.
2. *Pull non-bowling hand down to front pocket*: Restricts lateral torso collapse.
""".trimIndent()
            )
        ),
        PresetMovement(
            id = "shadow_boxing_combo",
            title = "Shadow Boxing 3-Punch Combo",
            subtitle = "Rotational Kinetic Energy Transfer (Jab - Cross - Lead Hook)",
            category = "Combat Sports",
            iconName = "sports_mma",
            durationSeconds = "00:03",
            description = "Jab-Cross-Lead Hook combination evaluating ground-up torque, rear foot pivot, hip rotation, and chin protection.",
            keyJointsAnalyzed = listOf("Pelvis / Hip Rotators", "Thoracic Spine", "Glenohumeral / Elbow", "Rear Ankle Plantarflexion"),
            sampleReport = BiomechanicalReport(
                movementName = "Boxing 3-Punch Kinetic Combination (Jab - Cross - Hook)",
                sportCategory = "Combat Sports / Boxing",
                phases = listOf(
                    KineticPhase("1. Stance & Lead Jab", "0.0s - 0.8s", "Weight 50/50 on balls of feet. Quick front step with lead hand snap, elbow extending straight without pre-telegraphing flare."),
                    KineticPhase("2. Rear Cross Kinetic Drive", "0.8s - 1.5s", "Rear foot pivots (squashing the bug), rear hip drives forward 45°, generating ground torque transmitted through core to straight right cross."),
                    KineticPhase("3. Weight Shift & Lead Hook", "1.5s - 2.2s", "Weight transitions cleanly to lead leg; lead hip abruptly rotates 90° inward with horizontal elbow carriage at eye level."),
                    KineticPhase("4. Reset to Defensive Guard", "2.2s - 2.8s", "Hands retract rapidly along strike vector to temple; chin remains tucked into clavicular hollow.")
                ),
                kinematics = KinematicAnalysis(
                    jointAngles = "Hook Elbow Angle: 92° (ideal right angle) | Rear Hip Internal Rotation: 38° | Shoulder Pronation at Impact: 85°.",
                    spinalAlignment = "Spine rotates cleanly along vertical axis without compensatory lateral lean. Head stays centered over base of support.",
                    baseOfSupport = "Staggered boxing stance maintains dynamic 60cm base of support throughout rapid pivots.",
                    kineticChainEfficiency = "92% kinetic chain transmission from ground contact to knuckle impact.",
                    efficiencyScore = 92
                ),
                injuryAssessment = InjuryRiskAssessment(
                    riskLevel = RiskSeverity.SAFE,
                    isSafe = true,
                    detectedFlaws = emptyList(),
                    detailedSummary = "No critical injury risks detected. Wrist maintains neutral alignment at impact preventing carpometacarpal sprains. Hip pivot buffers knee torsion."
                ),
                cues = listOf(
                    CorrectiveCue(1, "Keep rear hand glued to temple while throwing lead hook", "Defensive Kinetic Integrity", "Prevents counter-punch opening and maintains balance"),
                    CorrectiveCue(2, "Drive rear heel off the canvas on the cross", "Ground Force Production", "Maximizes kinetic chain velocity through kinetic floor drive"),
                    CorrectiveCue(3, "Turn knuckles over horizontally at terminal extension", "Pronation Mechanics", "Aligns index/middle metacarpals with radius bone")
                ),
                rawMarkdown = """
# 1. Movement Identification
Shadow Boxing Combination (1-2-3: Lead Jab, Rear Cross, Lead Hook)

# 2. Phase-by-Phase Breakdown
* **Jab Phase (0.0s - 0.8s)**: Linear force production with minimal wind-up. Elbow stays inside body line.
* **Cross Phase (0.8s - 1.5s)**: Rear metatarsal ground reaction forces initiated, sequential pelvic rotation (38°) into thoracic whip.
* **Lead Hook Phase (1.5s - 2.2s)**: Horizontal rotational velocity peaking at 620°/s trunk rotation, 90° elbow carriage.
* **Recovery (2.2s - 2.8s)**: Immediate guard restoration.

# 3. Kinematic Analysis
* **Joint angles and mobility**: Neutral wrist lockout at impact ensures load distributes directly into forearm bones.
* **Spinal alignment and core stability**: Rotational axis remains upright; no anterior head carriage flaw.
* **Footwork and base of support**: High agility on balls of feet; zero heel-flat deceleration lag.
* **Kinetic chain efficiency**: 92% transmission efficiency.

# 4. Injury Risk Assessment
No critical injury risks detected. Outstanding wrist and shoulder stabilization mechanics.

# 5. Corrective Cues
1. *Keep opposite hand pinned to jawbone*: Maintain structural symmetry.
2. *Pivot lead foot during hook turnaround*: Rotate lead heel outwards to safeguard the anterior cruciate ligament (ACL).
""".trimIndent()
            )
        ),
        PresetMovement(
            id = "conventional_deadlift",
            title = "Conventional Barbell Deadlift",
            subtitle = "Posterior Chain Force Production & Lumbar Integrity",
            category = "Weightlifting",
            iconName = "fitness",
            durationSeconds = "00:04",
            description = "High-load floor pull assessing hip wedge, lats engagement, lumbar spine rigidity, and lockout mechanics.",
            keyJointsAnalyzed = listOf("Lumbar Spine (L1-S1)", "Hip Extensors", "Thoracic Extensors", "Knee Extension Timing"),
            sampleReport = BiomechanicalReport(
                movementName = "Conventional Barbell Deadlift",
                sportCategory = "Powerlifting / Strength Training",
                phases = listOf(
                    KineticPhase("1. Setup & Wedge", "0.0s - 1.2s", "Bar over midfoot (1 inch from shins). Hips wedged between shoulders and knees, lats packed into back pockets."),
                    KineticPhase("2. Floor Separation (Liftoff)", "1.2s - 2.0s", "Leg press the floor away. Knee and hip angles open at identical rates keeping back angle constant until bar reaches patella."),
                    KineticPhase("3. Knee Clearance to Lockout", "2.0s - 3.2s", "Glutes fire violently forward into the bar. Shoulders finish behind bar with ribs pulled down, avoiding hyperextension."),
                    KineticPhase("4. Eccentric Return", "3.2s - 4.0s", "Hips push backward first until bar clears knees, then knees flex to place weight smoothly on platform.")
                ),
                kinematics = KinematicAnalysis(
                    jointAngles = "Starting Hip Angle: 74° | Starting Knee Angle: 108° | Torso Angle relative to floor: 32° | Lockout Hip Extension: 180°.",
                    spinalAlignment = "Spine exhibits rigid isometric stability. No flexion shear during initial floor break.",
                    baseOfSupport = "Midfoot balance with equal weight between heel and ball of foot.",
                    kineticChainEfficiency = "95% efficiency. Vertical bar path with zero forward drift around knees.",
                    efficiencyScore = 95
                ),
                injuryAssessment = InjuryRiskAssessment(
                    riskLevel = RiskSeverity.SAFE,
                    isSafe = true,
                    detectedFlaws = emptyList(),
                    detailedSummary = "No critical injury risks detected. Barbell stays within 1 cm of shin and thigh throughout ascent, keeping moment arm on lumbar spine minimized."
                ),
                cues = listOf(
                    CorrectiveCue(1, "Push the earth away like a leg press", "Initial Drive", "Ensures quadriceps initiate movement rather than lower back"),
                    CorrectiveCue(2, "Protect your armpits from tickling (lats engaged)", "Thoracic & Lat Stiffness", "Stabilizes lumbar vertebrae through thoracolumbar fascia tension"),
                    CorrectiveCue(3, "Stand tall at the top without leaning back", "Lockout Mechanics", "Prevents posterior spinal facet joint impingement")
                ),
                rawMarkdown = """
# 1. Movement Identification
Conventional Barbell Deadlift (Posterior chain compound movement)

# 2. Phase-by-Phase Breakdown
* **Setup & Tension (0.0s - 1.2s)**: Athlete creates full tension in posterior chain, pulling slack out of the barbell.
* **Liftoff to Knees (1.2s - 2.0s)**: Constant torso angle maintained, quad drive moves bar vertically.
* **Knees to Lockout (2.0s - 3.2s)**: Gluteal engagement locks hips without hyperextending lower back.
* **Eccentric Lowering (3.2s - 4.0s)**: Clean hip hinge return.

# 3. Kinematic Analysis
* **Joint angles and mobility**: Optimal 74° hip wedge prevents squatting the deadlift or stiff-legging.
* **Spinal alignment and core stability**: Neutral spinal positioning maintained under maximal isometric brace.
* **Footwork and base of support**: Center of mass stable across bilateral tripod feet.
* **Kinetic chain efficiency**: 95% kinetic chain transfer efficiency.

# 4. Injury Risk Assessment
No critical injury risks detected. Exemplary mechanical control under heavy load.

# 5. Corrective Cues
1. *Push through midfoot like a leg press machine*.
2. *Pack lats to lock bar tight to shins throughout the lift*.
""".trimIndent()
            )
        )
    )
}
