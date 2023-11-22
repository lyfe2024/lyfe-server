import org.springframework.boot.gradle.tasks.bundling.BootJar


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-aws
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-configuration-processor
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")

    // https://mvnrepository.com/artifact/io.github.oshai/kotlin-logging
    implementation("io.github.oshai:kotlin-logging:5.0.0")


    implementation(project(":util:common-util"))
    implementation(project(":core:lyfe-core"))
}

tasks.named<Jar>("jar") {
    enabled = true
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}