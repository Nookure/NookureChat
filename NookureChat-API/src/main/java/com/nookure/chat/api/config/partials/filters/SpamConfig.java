package com.nookure.chat.api.config.partials.filters;

import java.util.List;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class SpamConfig extends FilterConfig {
    @Setting
    private boolean enabled = true;

    @Setting
    private String denyMessage =
            "{prefix} <red>✕</red> <gray>Advertising links or IP addresses is <red>not allowed</red> (<red>spam</red>).</gray>";

    @Setting
    private List<String> whitelistedDomains = List.of("nookure.com", "lunna.dev", "minearte.net");

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getDenyMessage() {
        return denyMessage;
    }

    public List<String> getWhitelistedDomains() {
        return whitelistedDomains;
    }
}
