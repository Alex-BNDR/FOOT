pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

//buildscript {
//    dependencies {
//        classpath("com.google.gms:google-services:3.2.1")
//    }
//}
rootProject.name = "FOOT5"
include(":app")
 