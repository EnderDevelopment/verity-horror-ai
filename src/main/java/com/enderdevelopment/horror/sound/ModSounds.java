package com.enderdevelopment.horror.sound;

import com.enderdevelopment.horror.VerityMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
	public static final SoundEvent VERITY_AMBIENT = register("entity.verity.ambient");
	public static final SoundEvent VERITY_HURT = register("entity.verity.hurt");
	public static final SoundEvent VERITY_SCREAM = register("entity.verity.scream");
	public static final SoundEvent VERITY_TELEPORT = register("entity.verity.teleport");

	private static SoundEvent register(String name) {
		Identifier id = new Identifier(VerityMod.MOD_ID, name);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static void register() {
		// Static initialisers above already registered the sounds.
	}
}
