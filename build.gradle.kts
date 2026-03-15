import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml
import xyz.jpenilla.resourcefactory.bukkit.Permission

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
}

group = "dev.lyphium"
version = "1.0.1"
description = "Simple update suppressor plugin"

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    testCompileOnly("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")
}

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 21 on systems that only have JDK 11 installed for example.
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks {
    compileJava {
        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release = 21
    }
}

paperPluginYaml {
    main = "dev.lyphium.suppressor.UpdateSuppressor"
    load = BukkitPluginYaml.PluginLoadOrder.STARTUP
    apiVersion = "1.21.11"
    author = "Lyphion"
    permissions {
        register("suppressor.admin") {
            description = "Admin permission"
            default = Permission.Default.OP
        }
    }
}
