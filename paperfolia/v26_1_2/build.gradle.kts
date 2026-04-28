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