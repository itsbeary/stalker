plugins {
    id("java")
    id("io.freefair.lombok") version "9.2.0"
}

group = "net.karlite"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("net.dv8tion:JDA:6.4.1")
    implementation("ch.qos.logback:logback-classic:1.5.13")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.test {
    useJUnitPlatform()
}