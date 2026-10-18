# Light Client — Fabric 1.21.11

Client-side utility mod with:
- `J`: open ClickGUI
- `H`: toggle Movement HUD
- `C`: toggle Cobweb Info
- Toggle modules ON/OFF from the GUI
- Assign a keyboard key to each listed module from the GUI
- Training level selector from 1 to 6 (visual/training setting only)
- Movement speed HUD and cobweb status information

This project does not implement noclip, collision bypass, anticheat evasion, or movement cheats.

## Build
1. Install Java 21.
2. Open this folder in IntelliJ IDEA as a Gradle project.
3. Let Gradle sync.
4. Run `gradlew build` (Windows: `gradlew.bat build`).
5. The jar will be in `build/libs/`.

## Install
Copy the built jar to `.minecraft/mods`. Install Fabric Loader and Fabric API for Minecraft 1.21.11.

Note: dependency versions are set in `gradle.properties`; if Fabric publishes a different compatible API build, update `fabric_api_version` there.
