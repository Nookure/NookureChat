package com.nookure.chat.api.config.partials.filters;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class FloodConfig extends FilterConfig {
    @Setting
    private boolean enabled = true;

    @Setting
    private int maxRepetitions = 5;

    @Setting
    private String denyMessage =
            "{prefix} <red>✕</red> <gray>Please avoid repeating characters excessively (<red>flood</red>).</gray>";

    public int getMaxRepetitions() {
        return maxRepetitions - 1;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getDenyMessage() {
        return denyMessage;
    }
}
