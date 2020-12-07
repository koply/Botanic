[![](https://jitpack.io/v/MusaBrt/Botanic.svg)](https://jitpack.io/#MusaBrt/Botanic)

# Botanic
Moduler Discord bot engine.

Bot file path scheme:
```
| /libs
| /bionics
| Botanic.jar
| config.json
```

## How To Use
```
git clone https://github.com/MusaBrt/Botanic
cd botanic/
mvn install
```
After this commands, your Botanic build ready in `/target/Botanic/`

## How To Install For Develop Bionic
### With Maven:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.MusaBrt</groupId>
    <artifactId>Botanic</artifactId>
    <version>JITPACK-VERSION</version>
</dependency>
```
### With Gradle:
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.MusaBrt:Botanic:JITPACK-VERSION'
}
```

