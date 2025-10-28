plugins {
    java
    jacoco
    application
}

group = "edu.trincoll"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // AssertJ for fluent assertions
    testImplementation("org.assertj:assertj-core:3.27.6")

    // Mockito for mocking (useful for Command pattern tests)
    testImplementation("org.mockito:mockito-core:5.20.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.20.0")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-Xshare:off","-XX:+EnableDynamicAgentLoading")
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        html.required = true
        csv.required = false
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

// Add coverage verification to check task
tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

application {
    mainClass = "edu.trincoll.game.demo.GameDemo"
}
