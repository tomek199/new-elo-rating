dependencies {
    implementation(project(":domain"))
    implementation("org.springframework:spring-context:5.3.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")
    implementation("com.github.EventStore:EventStoreDB-Client-Java:trunk-SNAPSHOT")
    testImplementation("org.mockito:mockito-inline:3.3.3")
}