plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(group = "io.appium", name = "java-client", version = "7.6.0")
    testImplementation("org.testng:testng:7.12.0")
}

tasks.test {
    // Attach Test Orchestrator: -javaagent:<agent.jar>=<config.yml>
    // Build ID groups results in Reporter; CI sets BUILD_ID, local runs fall back to "local".
    jvmArgs(
        "-javaagent:${projectDir}/lib/smart-agent-1.0-SNAPSHOT.jar=${projectDir}/lib/config.yml",
        "-Dbuild.id=${System.getenv("BUILD_ID") ?: "local"}"
    )
    useTestNG()
}