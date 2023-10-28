<p align="center">
    <img src="https://user-images.githubusercontent.com/50689727/130094678-7640882a-e9dc-4c09-837c-e9eb1c99b0cd.png" alt="Simple armor hud logo" width="72px" height="72px"/>
</p>
    
<h1 align="center">Simple armor hud</h1>

<p align="center">
    <img src="https://img.shields.io/badge/for%20MC-1.17.x,%201.18.x,%201.19.x,%201.20.x-green" alt="Minecraft version"/>
    <img src="https://img.shields.io/github/v/release/LegoRaft/simple-armor-hud?color=yellow" alt="Release"/>
    <img src="https://img.shields.io/modrinth/dt/tJflAtvJ?label=modrinth" alt="Modrinth downloads"/>
    <img src="https://img.shields.io/github/downloads/legoraft/simple-armor-hud/total" alt="Github downloads"/>
</p>

## Information
This mod shows your armor above the foodbar in the in-game hud. It also moves armor up when underwater and down when in creative and has a few config options for compatibility with other mods. You can toggle the armor hud on or off with a keybind, configurable through the normal menu.

### Features
<img src="https://user-images.githubusercontent.com/50689727/130084592-5a35579a-f300-4c6e-b6ad-9b6bd620904c.png"  alt="armor hud third person" width="640"/> <br>
_Shows armor you're currently wearing_
<img src="https://cdn.modrinth.com/data/tJflAtvJ/images/dcc817d1be3765a8af5ef581bff1abe909c77e47.png" alt="armor hud underwater" width="640"/><br>
_Moves armor up underwater_

### Bugs
If you have any bug reports or a suggestion for the mod leave them [here](https://github.com/LegoRaft/simple-armor-hud/issues). If you have any coding experience and want to help out with development, fork the repository and open a [pull request](https://github.com/legoraft/simple-armor-hud/pulls).

### Translations
This mod is available with english and dutch translations, if you know any other translations, create a [pull request](https://github.com/legoraft/simple-armor-hud/pulls) with a new [lang](https://github.com/legoraft/simple-armor-hud/tree/main/src/main/resources/assets/simple-armor-hud/lang) file with your translation.

### Dependencies
Simple armor hud requires the [Fabric API](https://modrinth.com/mod/fabric-api)

### Installation
Releases for the mod are found on [Modrinth](https://modrinth.com/mod/simple-armor-hud), [Releases](https://github.com/LegoRaft/simple-armor-hud/releases) and on [Curseforge](https://www.curseforge.com/minecraft/mc-mods/simple-armor-hud). After downloading, you can put the `.jar` file in your `mods` folder. If you don't have fabric installed, take a look at this [installation tutorial](https://fabricmc.net/wiki/install) _Note: Simple armor hud **requires** the Fabric API_

### Build
To build from source, follow these steps:
1. Open a terminal and clone the repository using `git clone https://github.com/legoraft/simple-armor-hud`.
2. Go into this directory using `cd <location of cloned repo>`.
3. Run `./gradlew build` on linux or macos or `gradlew build` on windows.
4. Get the mod file from the `/build/libs` folder.
