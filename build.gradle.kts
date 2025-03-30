plugins {
    java
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.babich"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Core
    implementation("org.springframework:spring-context:5.3.20")
    implementation("org.springframework:spring-core:5.3.20")
    implementation("org.springframework:spring-jdbc:5.3.20")

    // AOP
    implementation("org.springframework:spring-aop:5.3.20")
    implementation("org.aspectj:aspectjweaver:1.9.7")

    // PostgreSQL
    implementation("org.postgresql:postgresql:42.3.3")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    // Servlet API
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
