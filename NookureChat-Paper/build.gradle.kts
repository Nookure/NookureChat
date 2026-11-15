import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
  id("de.eldoria.plugin-yml.paper") version "0.9.0"
}

dependencies {
  compileOnly(project(":NookureChat-API"))
  compileOnly(libs.paperApi)
  compileOnly(libs.bundles.adventure)
  compileOnly(libs.vault)
  compileOnly(libs.bstatsBukkit)
}

paper {
  name = "NookureChat"
  version = rootProject.version as String
  description = "A simple chat plugin for Paper"
  website = "https://nookure.com"
  main = "com.nookure.chat.paper.bootstrap.ChatBootstrapper"
  authors = listOf("Lunna")
  apiVersion = "26.3"

  serverDependencies {
    register("LuckPerms") {
      required = false
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
    }

    register("PlaceholderAPI") {
      required = false
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
    }

    register("Vault") {
      required = false
      load = PaperPluginDescription.RelativeLoadOrder.BEFORE
    }
  }
}