# bedrock
<span>
    <a href=""><img alt="Maven metadata URL" src="https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.grabsky.cloud%2Freleases%2Fcloud%2Fgrabsky%2Fbedrock%2Fmaven-metadata.xml&style=for-the-badge&logo=gradle&label=%20"></a>
    <a href=""><img alt="GitHub Workflow Status (with event)" src="https://img.shields.io/github/actions/workflow/status/Grabsky/bedrock/gradle.yml?style=for-the-badge&logo=github&logoColor=white&label=%20"></a>
    <a href=""><img alt="CodeFactor Grade" src="https://img.shields.io/codefactor/grade/github/Grabsky/bedrock/main?style=for-the-badge&logo=codefactor&logoColor=white&label=%20"></a>
</span>
<p></p>

Multi-purpose library aiming to make development of **[PaperMC/Paper](https://github.com/PaperMC/Paper)** plugins faster.

<br />

## Requirements
Requires **Java 17** (or higher) and **Paper 1.20.1** (or higher).

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
You can also use [GitHub Packages](https://github.com/Grabsky/bedrock/packages/) - read more about that [here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#using-a-published-package).

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
