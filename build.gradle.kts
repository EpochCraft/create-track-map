import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.modrinth.minotaur.ModrinthExtension
import com.modrinth.minotaur.TaskModrinthUpload
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace

plugins {
  kotlin("jvm") version "1.8.10"
  java
  id("net.neoforged.gradle") version "[6.0.13, 6.2)"
  id("com.github.johnrengelman.shadow") version "7.1.2"
  id("com.modrinth.minotaur") version "2.+"
}

val mod_version: String by project
val minecraft_version: String by project
val maven_group: String by project
val archives_base_name: String by project
val create_version_short: String by project

version = mod_version
group = maven_group

val archives_version = "$mod_version+mc$minecraft_version-neoforge"

repositories {
  mavenCentral()
  maven("https://jitpack.io")  // MixinExtras, Fabric ASM
  maven("https://maven.jamieswhiteshirt.com/libs-release")  // Reach Entity Attributes
  maven("https://api.modrinth.com/maven")
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(kotlin("reflect"))
  // Use Kotlin to provide kotlinx.serialization
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

  implementation("org.fusesource.jansi:jansi:2.4.0")

  // Exclude shaded dependencies to avoid conflicts
  implementation("some.dependency:example:1.0.0") {
    exclude(group = "org.fusesource.jansi", module = "jansi")
  }
}

tasks.withType<ShadowJar> {
  relocate("com.google", "createtrackmap.shaded.com.google")
  relocate("org.apache", "createtrackmap.shaded.org.apache")
  relocate("org.fusesource", "createtrackmap.shaded.org.fusesource")
}
