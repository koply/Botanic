![](http://image-write-app.herokuapp.com/?x=880&y=33&size=130&text=koply&url=https%3A%2F%2Fimage-write-app.herokuapp.com%2F%3Fx%3D185%26y%3D25%26size%3D150%26text%3DBotanic%26url%3Dhttps%3A%2F%2Fwww.afcapital.ru%2Fa%2Fpgs%2Fimages%2Fcontent-grid-bg.png)
[![](https://jitpack.io/v/MusaBrt/Botanic.svg)](https://jitpack.io/#MusaBrt/Botanic)

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

