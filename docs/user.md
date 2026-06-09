# User docs

After installing Simple Armor Hud, I'd recommend adding [Mod Menu](https://modrinth.com/project/mOgUt4GM) to configure the mod. You can also do configuration through the configuration file.

## Configuration

To configure Simple Armor Hud, simply open the settings menu through Mod Menu. There you'll find all the settings to adjust position, visibility and mod compatibility. The armor hud toggle keybind is configured through Minecraft's controls options.

The configuration file has a few different options to adjust. There's one option that isn't a boolean, all others are.

- `armor_hud`: Sets the visibility of the armor hud (`true`/`false`)
- `disable_armor_bar`: Sets the visibility of the vanilla armor bar (`true`/`false`)
- `position`: Sets the position of the armor hud (`FOODBAR`, `HEALTHBAR`, `HOTBAR_LEFT`, `HOTBAR_RIGHT`)
- `right_to_left`: Switches the direction of the hud (`true`/`false`)
- `trim_empty_slots`: Aligns the hud to center and trims all empty slots out (`true`/`false`)
- `better_mount_hud`: Compatibility mode for the [Better Mount HUD](https://modrinth.com/project/kqJFAPU9) mod (`true`/`false`)
- `double_hotbar`: Compatibility mode for [Double Hotbar](https://modrinth.com/project/OpX7IHIc) (`true`/`false`)

## Issues

If the armor hud is covering your normal HUD, try setting the compatibility mode for your mod. If your mod doesn't have a compatibility mode, open an [issue](https://github.com/legoraft/simple-armor-hud/issues) with a feature request.
