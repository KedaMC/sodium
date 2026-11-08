plugins {
    id("multiloader-base")
    id("maven-publish")
}

tasks {
    processResources {
        inputs.property("version", version)

        filesMatching(listOf("fabric.mod.json")) {
            expand(mapOf("version" to inputs.properties["version"]))
        }
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.FAIL
        from(rootDir.resolve("LICENSE.md"))
    }
}

publishing {
    // Each platform is responsible for their own "publications".

    repositories {
        val isReleaseBuild = project.hasProperty("build.release")
        val caffeineMCMavenUsername: String? by project // reads from ORG_GRADLE_PROJECT_caffeineMCMavenUsername
        val caffeineMCMavenPassword: String? by project // reads from ORG_GRADLE_PROJECT_caffeineMCMavenPassword

        maven {
            name = "CaffeineMC"
            url = uri("https://maven.caffeinemc.net".let {
                if (isReleaseBuild) "$it/releases" else "$it/snapshots"
            })

            credentials {
                username = caffeineMCMavenUsername
                password = caffeineMCMavenPassword
            }
        }
    }
}
