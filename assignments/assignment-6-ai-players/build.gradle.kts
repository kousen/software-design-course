plugins {
    java
    application
    jacoco
}

group = "edu.trincoll"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    // Spring Boot & Spring AI
    implementation("org.springframework.boot:spring-boot-starter:3.4.1")
    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter:1.0.3")
    implementation("org.springframework.ai:spring-ai-anthropic-spring-boot-starter:1.0.3")
    implementation("org.springframework.ai:spring-ai-vertex-ai-gemini-spring-boot-starter:1.0.3")

    // JSON processing
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.2")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.15")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("edu.trincoll.game.GameApplication")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/test/html"))
    }
}

tasks.register("runGame") {
    group = "application"
    description = "Run the AI-powered game"
    dependsOn("run")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    args = project.findProperty("appArgs")?.toString()?.split("\\s+") ?: emptyList()
}
