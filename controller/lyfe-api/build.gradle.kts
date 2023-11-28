dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-aws
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    implementation(project(":util:common-util"))
    implementation(project(":core:lyfe-core"))
    implementation(project(":infrastructure:datastore-mysql"))
    implementation(project(":infrastructure:aws"))
}