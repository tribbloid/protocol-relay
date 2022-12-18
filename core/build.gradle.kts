val vs = versions()

val splainVRelease: String = "1.0.1"

val splainVFallback: String = run {
    vs.splainV.ifEmpty { splainVRelease }
}

dependencies {

    testFixturesApi("io.tryp:splain_${vs.scalaV}:${splainVFallback}")
}