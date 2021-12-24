import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    jacoco
    id("org.sonarqube")
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version "1.5.21"
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_11
extra["springCloudVersion"] = "2020.0.4"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.3")
    implementation("com.aventrix.jnanoid:jnanoid:2.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:${project.getKotlinPluginVersion()}")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.31.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.mockito", module = "mockito-core")
        exclude(group = "org.mockito", module = "mockito-junit-jupiter")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

sonarqube { }

jacoco {
    toolVersion = "0.8.7"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
    }
}

springBoot {
    buildInfo()
}
