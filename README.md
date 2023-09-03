# grabsky/bedrock
<span>
    <a href="#"><img alt="GitHub Workflow Status (with event)" src="https://img.shields.io/github/actions/workflow/status/Grabsky/bedrock/gradle.yml?style=for-the-badge&logo=github&logoColor=white&label=%20"></a>
    <a href="#"><img alt="CodeFactor Grade" src="https://img.shields.io/codefactor/grade/github/Grabsky/bedrock/main?style=for-the-badge&logo=codefactor&logoColor=white&label=%20"></a>
</span>
<p></p>

Multi-purpose library aiming to make development of **[PaperMC/Paper](https://github.com/PaperMC/Paper)** plugins faster.

<br />

> **Warning**  
> Breaking changes are likely to happen before a stable release.

<br />

## Requirements
Requires **Java 17** (or higher) and **Paper 1.20.1* (or higher).

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
    implementation 'cloud.grabsky:bedrock:[_VERSION_]'
}
```
Consider **[relocating](https://imperceptiblethoughts.com/shadow/configuration/relocation/)** to prevent version mismatch issues. This can be ignored if your plugin is a **[Paper plugin](https://docs.papermc.io/paper/dev/getting-started/paper-plugins)** with **[isolated classloader](https://docs.papermc.io/paper/dev/getting-started/paper-plugins#classloading-isolation)**.

<br />

## Building (Linux)
```shell
# Cloning repository
$ git clone https://github.com/Grabsky/bedrock.git
# Entering cloned repository
$ cd ./bedrock
# Compiling and publishing to maven local
$ ./gradlew clean publishToMavenLocal
```

<br />

## Contributing
This project is open for contributions. Help in regards of improving performance, adding new features or fixing bugs is greatly appreciated.
