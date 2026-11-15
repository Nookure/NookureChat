plugins {
  id("java")
  id("com.gradleup.shadow") version "9.6.1"
  id("xyz.jpenilla.run-paper") version "3.1.0"
  alias(libs.plugins.spotless)
}

group = "com.nookure.chat"
version = "1.1.2"

dependencies {
  // Implements projects
  implementation(project(":NookureChat-API"))
  implementation(project(":NookureChat-Paper"))
  // Implements libraries
  implementation(libs.guice)
  implementation(libs.configurateYaml)
  implementation(libs.bstatsBukkit)
}

tasks.shadowJar {
  relocate("com.google.inject", "com.nookure.chat.libs.guice")
  relocate("com.google.common", "com.nookure.chat.libs.guava")
  relocate("com.google.thirdparty", "com.nookure.chat.libs.guava.thirdparty")
  relocate("com.google.errorprone", "com.nookure.chat.libs.errorprone")
  relocate("com.google.j2objc", "com.nookure.chat.libs.j2objc")
  relocate("org.bstats", "com.nookure.chat.libs.bstats")
  relocate("org.spongepowered.configurate", "com.nookure.chat.libs.configurate")
  relocate("org.yaml.snakeyaml", "com.nookure.chat.libs.snakeyaml")
  relocate("io.leangen.geantyref", "com.nookure.chat.libs.geantyref")

  archiveFileName.set("NookureChat-${version}.jar")
}

allprojects {
  apply(plugin = "com.gradleup.shadow")
  apply(plugin = "java")
  apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)

  repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://papermc.io/repo/repository/maven-releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
  }

  tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
  }

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(25))
    }
  }

  dependencies {
    compileOnly(rootProject.libs.guice)
    compileOnly(rootProject.libs.adventureApi)
  }

  spotless {
    java {
      palantirJavaFormat(rootProject.libs.plugins.palantir.java.format.get().version.displayName)
      removeUnusedImports();
      importOrder()
      trimTrailingWhitespace()
      endWithNewline()
      formatAnnotations()
      targetExclude("build/**", "out/**", "generated/**", "resources/**", "test/**")
    }
  }
}


tasks {
  runServer {
    minecraftVersion("26.3")
  }
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
  javaLauncher = javaToolchains.launcherFor {
    languageVersion = JavaLanguageVersion.of(25)
  }
}