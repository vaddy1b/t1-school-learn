plugins {
    java
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.babich"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17) // или 11
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Core
    implementation("org.springframework:spring-context:5.3.32")
    implementation("org.springframework:spring-core:5.3.32")
    implementation("org.springframework:spring-jdbc:5.3.32")
    implementation("org.springframework:spring-web:5.3.32")

    // AOP
    implementation("org.springframework:spring-aop:5.3.32")
    implementation("org.aspectj:aspectjweaver:1.9.7")

    // PostgreSQL
    implementation("org.postgresql:postgresql:42.3.3")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    // SLF4J API
    implementation("org.slf4j:slf4j-api:1.7.36")

    // Logback (реализация SLF4J)
    implementation("ch.qos.logback:logback-classic:1.2.11")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
