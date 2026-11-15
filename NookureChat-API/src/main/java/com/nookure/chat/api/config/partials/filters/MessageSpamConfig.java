package com.nookure.chat.api.config.partials.filters;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class MessageSpamConfig extends FilterConfig {
    @Setting
    private boolean enabled = true;

    @Setting
    private int cooldown = 3;

    @Setting
    private String denyMessage =
            "{prefix} <#FFAA00>⚠</#FFAA00> <gray>You are typing too fast! Please wait <#FFAA00>{time}s</#FFAA00>.</gray>";

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getDenyMessage() {
        return denyMessage;
    }

    public int getCooldown() {
        return cooldown;
    }
}
