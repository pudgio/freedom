# OneTap Mod

One Tap Anything - A Necesse mod that enables free crafting.

## Current Status

✅ **Working:**
- Free crafting is ENABLED by default
- All recipes can be crafted without materials
- Settings are saved to `onetap_config.properties` file

❌ **Not Yet Implemented:**
- Settings UI button in pause menu (form exists but not accessible in-game yet)
- Keyboard shortcut to open settings
- In-game toggle command

## How to Use

### Toggle Free Crafting

Currently, you can toggle free crafting by editing the config file:

1. Close the game
2. Navigate to your Necesse folder (usually `%APPDATA%\Necesse`)
3. Edit `onetap_config.properties`
4. Change `freeCrafting=true` to `freeCrafting=false` (or vice versa)
5. Restart the game

### Future Plans

The mod includes a settings form UI (`FreedomSettings`) but it's not yet integrated into the game's menu system. To make it accessible, you would need to:

1. Create a patch for Necesse's pause menu to add a button
2. Or add a keyboard shortcut listener
3. Or create a chat command

These features require deeper knowledge of the Necesse API which is not well documented.

## Development

The mod compiles successfully and the free crafting functionality works perfectly. The UI form is ready but just needs to be hooked up to the game's menu system.

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew runClient
```

## Files

- `OneTap.java` - Main mod entry point, handles config loading/saving
- `patches/FreeCrafting.java` - ByteBuddy patches that enable free crafting
- `forms/FreedomSettings.java` - Settings UI (not yet accessible in-game)
