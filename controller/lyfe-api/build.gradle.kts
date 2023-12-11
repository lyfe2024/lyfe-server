dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("io.kotest:kotest-runner-junit5:5.8.0")
    implementation("io.kotest:kotest-assertions-core:5.8.0")
    implementation("io.kotest:kotest-property:5.8.0")

    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-aws
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    implementation(project(":util:common-util"))
    implementation(project(":model"))
    implementation(project(":usecase"))
    implementation(project(":infrastructure:datastore-mysql"))
    implementation(project(":infrastructure:aws"))
}