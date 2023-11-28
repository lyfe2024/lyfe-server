import org.springframework.boot.gradle.tasks.bundling.BootJar

apply(plugin = "kotlin-jpa")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(mapOf("path" to ":infrastructure:aws")))
    implementation(project(mapOf("path" to ":usecase")))
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation(project(":util:common-util"))
    implementation(project(":model"))
    implementation(project(":usecase"))
}

tasks.named<Jar>("jar") {
    enabled = true
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}