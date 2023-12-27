import org.springframework.boot.gradle.tasks.bundling.BootJar


dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // feignClient
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

    //kotest + mockk
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.6.0")
    testImplementation("io.kotest:kotest-property-jvm:4.6.0")
    testImplementation("io.kotest:kotest-extensions-spring:4.4.0")
    testImplementation("io.mockk:mockk:1.13.7")

    implementation(project(":model"))
    implementation(project(":util:common-util"))
}

tasks.named<Jar>("jar") {
    enabled = true
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}