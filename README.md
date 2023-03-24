# grabsky/bedrock
[![](https://github.com/Grabsky/bedrock/actions/workflows/gradle.yml/badge.svg)](https://github.com/Grabsky/bedrock/actions/workflows/gradle.yml)
[![](https://www.codefactor.io/repository/github/grabsky/bedrock/badge/main)](https://www.codefactor.io/repository/github/grabsky/bedrock/overview/main)  
Multi-purpose library aiming to make development of **[PaperMC/Paper](https://github.com/PaperMC/Paper)** plugins faster.

<br />

## Requirements
Requires **Java 17** (or higher) and **Paper 1.19.4** (or higher).

<br />

## Getting Started
To use this project in your plugin, add following repository:
```groovy
repositories {
    maven { url = 'https://repo.grabsky.cloud/releases' }
}
```
Then specify dependency:
```groovy
dependencies {
    implementation 'cloud.grabsky:bedrock:[version]'
}
```
Consider **[relocating](https://imperceptiblethoughts.com/shadow/configuration/relocation/)** to prevent version mismatch issues.

<br />

## Building (Linux)
```shell
# Cloning repository
$ git clone https://github.com/Grabsky/bedrock.git
# Entering cloned repository
$ cd ./configuration
# Compiling and publishing to maven local
$ ./gradlew clean test publishToMavenLocal
```

<br />

## Contributing
This project is open for contributions. Help in regards of improving performance, adding new features or fixing bugs is greatly appreciated.
