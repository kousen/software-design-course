plugins {
    java
    application
    jacoco
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "edu.trincoll"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

extra["springAiVersion"] = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot & Spring AI
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.ai:spring-ai-starter-model-anthropic")
    implementation("org.springframework.ai:spring-ai-starter-model-openai")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

application {
    mainClass.set("edu.trincoll.game.GameApplication")
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-XX:+EnableDynamicAgentLoading", "-Xshare:off", "--enable-native-access=ALL-UNNAMED")
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

tasks.register<JavaExec>("runSolution") {
    group = "application"
    description = "Run the solution version (complete implementation)"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("edu.trincoll.solutions.GameApplicationSolution")
    standardInput = System.`in`
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    args = project.findProperty("appArgs")?.toString()?.split("\\s+") ?: emptyList()
}
