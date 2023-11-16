apply(plugin = "kotlin-jpa")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation(project(":util:common-util"))
    implementation(project(":core:lyfe-core"))
}