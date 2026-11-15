package com.nookure.chat.api.config.partials.filters;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class BannedWordsConfig extends FilterConfig {
    @Setting
    private boolean enabled = true;

    @Setting
    private String denyMessage =
            "{prefix} <red>✕</red> <gray>That language is <red>not allowed</red> on this server.</gray>";

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getDenyMessage() {
        return denyMessage;
    }
}
