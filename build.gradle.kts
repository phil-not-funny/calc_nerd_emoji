plugins {
    id("java")
    id("application")
}

group = "com.pnf"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.pnf.calc_nerdemoji.Main")
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Main-Class"] = "com.pnf.calc_nerdemoji.Main"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.jline:jline:3.25.1")
    implementation("org.fusesource.jansi:jansi:2.4.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
}

tasks.test {
    useJUnitPlatform()
}
