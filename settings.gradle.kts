
val noVerify: String? by settings

val noSpike: String? by settings

fun isEnabled(profile: String?): Boolean {
    val result = profile.toBoolean() || profile == ""
    return result
}

include("prover-commons")
project(":prover-commons").projectDir = file("prover-commons/core")

include(
    ":core"
)

pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
    // maven("https://dl.bintray.com/kotlin/kotlin-dev")
}
