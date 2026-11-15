package com.nookure.chat.api.config.partials.filters;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class CapitalizationFilterConfig extends FilterConfig {
    @Setting
    private boolean enabled = true;

    @Setting
    boolean addDot = true;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean addDot() {
        return addDot;
    }

    @Override
    public String getDenyMessage() {
        return null;
    }
}
