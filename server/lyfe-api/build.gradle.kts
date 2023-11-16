dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation(project(":util:common-util"))
    implementation(project(":core:lyfe-core"))
    implementation(project(":infrastructure:datastore-mysql"))
}