plugins {
  id("net.kyori.blossom") version "2.2.0"
}

dependencies {
  compileOnly(libs.paperApi)
  compileOnly(libs.adventureLegacy)
  compileOnly(libs.adventureApi)
  compileOnly(libs.miniMessage)
  compileOnly(libs.configurateYaml)
  compileOnly(libs.vault)
  compileOnly(libs.placeholderApi)
  compileOnly(libs.lucko.luckperms)

  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testImplementation(libs.bundles.adventure)
  testImplementation(libs.guice)
}

tasks.test {
  useJUnitPlatform()
}

sourceSets {
  main {
    blossom {
      javaSources {
        property("version", rootProject.version.toString())
      }
    }
  }
}