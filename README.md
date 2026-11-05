# Verity Horror AI Mod

Verity Horror is a popular-style **horror mod** for **Minecraft 1.20.1 (Fabric)** that adds an intelligent stalker — **Verity** — a creature that does not simply chase you. It *hunts you*. This Minecraft mod uses AI-style behaviors to make Verity learn, stalk, and strike. Verity stays out of the light, teleports out of sight, studies you from the dark, and only attacks when you are alone, in darkness, and vulnerable. The mod introduces a new hostile mob with advanced goal-based AI, a unique defensive item, and immersive sound design to create a genuinely terrifying experience.

## Install

1. Install the **Fabric Loader** for Minecraft **1.20.1** (from fabricmc.net).
2. Install the **Fabric API** jar for 1.20.1 into your `mods` folder.
3. Place this mod's jar (`horror-mod-1.0.0.jar`) into the `mods` folder.
4. Launch the game.

## Use

Verity naturally spawns in the overworld at night. To survive, keep your torches lit and stay near other players. Use the Hollow Bell defensively when Verity corners you. For testing, you can spawn Verity with:

```
/give @s horror:verity_spawn_egg
/give @s horror:hollow_bell
```

### Key Mechanics

- **Lights are your friend** — Verity flees into shadow in bright light and only pursues players who stand in darkness (block light ≤ 3).
- **Teleport stalking** — When you are 16–48 blocks away, Verity teleports closer to you out of sight before studying you again.
- **Awareness meter** — Verity accumulates awareness while you stay in the dark. At full awareness it commits and lets out an unmissable scream, then attacks.
- **Solitary hunter** — It will not hunt you while other players are nearby.
- **The Hollow Bell** — your one defensive tool. Ring it to give yourself Night Vision, briefly slow Verity, and make it glow — at the cost of revealing your exact position to it.

### Build from Source

For developers, this project uses **Fabric Loom**. Run:

```bash
./gradlew build
```

The mod jar appears under `build/libs/`.

### Custom Content (Optional)

- Replace the texture reference in `VerityEntityRenderer.TEXTURE` with your own `assets/horror/textures/entity/verity.png` (64×64).
- Add `.ogg` sound files to `assets/horror/sounds/` per `sounds.json`.
- Add `assets/horror/icon.png` (128×128) for a custom launcher icon.

---

More Minecraft mod projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/5a1ac645-4b48-4838-a058-01370ad20f79?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=5a1ac645-4b48-4838-a058-01370ad20f79
