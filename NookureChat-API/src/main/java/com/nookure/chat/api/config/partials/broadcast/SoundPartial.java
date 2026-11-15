package com.nookure.chat.api.config.partials.broadcast;

import org.bukkit.Sound;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class SoundPartial {
    @Setting
    private boolean enabled = true;

    @Setting
    private Sound sound = Sound.ENTITY_PLAYER_LEVELUP;

    @Setting
    private float volume = 1.0F;

    @Setting
    private float pitch = 1.0F;

    public boolean isEnabled() {
        return enabled;
    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }
}
