package com.nookure.chat.api.config.partials.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class ClearChatPartial {
    @Setting
    private boolean enabled = true;

    @Setting
    private String successful =
            "{prefix} <#74EE8A>✔</#74EE8A> <gray>The chat has been <#74EE8A>cleared successfully</#74EE8A>.</gray>";

    public boolean isEnabled() {
        return enabled;
    }

    public String getSuccessful() {
        return successful;
    }
}
