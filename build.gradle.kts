plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "dev.krysztal"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.ow2.asm:asm:9.5")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}
