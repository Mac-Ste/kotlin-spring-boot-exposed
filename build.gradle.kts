import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("plugin.spring") version "2.0.0"
}

group = "com.macste"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val logback_version = "1.5.6"
    val exposedVersion = "0.51.1"
    val jacksonModuleKotlin = "2.17.1"
    val postgresDriverVersion = "42.7.3"
    val kotestVersion = "5.9.1"
    val mockkVersion = "1.13.11"
    val postgresTestContainerVersion = "1.19.8"

    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlin")
    implementation("org.postgresql:postgresql:$postgresDriverVersion")

    /*
        Spring Boot
     */
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")


    /*
        Exposed
     */
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    //implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    //implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")
    // or
    //implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    // or
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")

    //implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
    //implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:$exposedVersion")

    /*
        TEST
     */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.testcontainers:postgresql:$postgresTestContainerVersion")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

kotlin {
    jvmToolchain(17)
}