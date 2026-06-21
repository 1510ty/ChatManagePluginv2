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
plugins {
    java
    // Shadowプラグインを追加
    id("com.gradleup.shadow") version "9.4.1"
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
    implementation("redis.clients:jedis:7.5.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.jar {
    archiveFileName.set("${rootProject.name}-${project.version}-PaperFolia26.1.2.jar")
}

tasks {
    // 通常の jar タスク（Jedisなし）の設定
    jar {
        // 通常のjarは名前が被らないように classifier を付けておくか、
        // もしくは archiveFileName を設定しない（デフォルトのままにする）
        archiveClassifier.set("raw")
    }

    // shadowJar タスク（Jedis入り）の設定
    shadowJar {
        // こちらを「本命」の名前に設定する
        archiveFileName.set("${rootProject.name}-${project.version}-PaperFolia26.1.2.jar")
        archiveClassifier.set("") // classifierを空にする

        // 再配置
        relocate("redis.clients.jedis", "com.mc1510ty.libs.jedis")
    }

    build {
        dependsOn(shadowJar)
    }
}