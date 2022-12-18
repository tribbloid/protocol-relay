val vs = versions()

val splainVRelease: String = "1.0.1"

val splainVFallback: String = run {
    vs.splainV.ifEmpty { splainVRelease }
}

dependencies {

    testFixturesApi("org.scalatest:scalatest_${vs.scalaBinaryV}:${vs.scalaTestV}")

    testFixturesApi("io.tryp:splain_${vs.scalaV}:${splainVFallback}")

    api("org.json4s:json4s-jackson_${vs.scalaBinaryV}:3.5.5")
    api("com.google.guava:guava:16.0.1")

}