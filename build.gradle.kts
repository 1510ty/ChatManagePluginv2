//        ChatManagePluginv2
//        Copyright (C) 2026  1510ty
//
//        This program is free software: you can redistribute it and/or modify
//        it under the terms of the GNU General Public License as published by
//        the Free Software Foundation, either version 3 of the License, or
//        (at your option) any later version.
//
//        This program is distributed in the hope that it will be useful,
//        but WITHOUT ANY WARRANTY; without even the implied warranty of
//        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//        GNU General Public License for more details.
//
//        You should have received a copy of the GNU General Public License
//        along with this program.  If not, see <https://www.gnu.org/licenses/>.
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

val gitCommit: String? by project
val buildTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))

version = if (!gitCommit.isNullOrBlank()) {
    "$buildTime-$gitCommit"
} else {
    "0.0.6"
}

group = "com.mc1510ty"

subprojects {
    version = rootProject.version

    apply(plugin = "java")
    apply(plugin = "java-library")

    // --- ここに一括設定を追加 ---
    tasks.withType<ProcessResources> {
        val props = mapOf("version" to project.version)
        inputs.properties(props)

        filteringCharset = "UTF-8"
        filesMatching("**/paper-plugin.yml") {
            expand(props)
        }
        filesMatching("**/plugin.yml") {
            expand(props)
        }
    }

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://maven.fabricmc.net/")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}