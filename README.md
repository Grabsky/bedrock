# bedrock

![Latest Release](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fapi.github.com%2Frepos%2FGrabsky%2Fbedrock%2Freleases%2Flatest&query=tag_name&logo=gradle&style=for-the-badge&label=%20&labelColor=%231C2128&color=%23454F5A)
![Build Status](https://img.shields.io/github/actions/workflow/status/Grabsky/bedrock/gradle.yml?style=for-the-badge&logo=github&logoColor=white&label=%20)
![CodeFactor Grade](https://img.shields.io/codefactor/grade/github/Grabsky/bedrock/main?style=for-the-badge&logo=codefactor&logoColor=white&label=%20)

Multi-purpose library aiming to make development of **[PaperMC/Paper](https://github.com/PaperMC/Paper)** plugins faster.

<br />

## Requirements
Requires **Java 21** (or higher) and **Paper 1.20.1** (or higher).

<br />

## Getting Started
We're using **[GitHub Gradle Registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry)** for publications and thus may require extra steps for the project to build properly.
```groovy
repositories {
    maven { url = "https://maven.pkg.github.com/grabsky/bedrock"
        credentials {
            username = findProperty("gpr.actor") ?: System.getenv("GITHUB_ACTOR")
            password = findProperty("gpr.token") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("cloud.grabsky:bedrock:[_VERSION_]")
}
```

<br />

## Building
```shell
# Cloning the repository.
$ git clone https://github.com/Grabsky/bedrock.git
# Entering the cloned repository.
$ cd bedrock
# Compiling and publishing to maven local.
$ gradlew clean publishToMavenLocal
```

<br />

## Contributing
This project is open for contributions. Help in regards of improving performance, adding new features or fixing bugs is greatly appreciated.
