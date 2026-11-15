package com.nookure.chat.api.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class JoinMotdConfig {
    @Setting
    private boolean enabled = true;

    @Setting
    private String motd = """
      <dark_gray><st>-----------------------------------------------------</st></dark_gray>
      {center}<red><b>Welcome to our server!</b></red>

      <gray>Hello <white>%player_name%</white>, we're glad to have you back!</gray>
      <gray>Type <gold>/help</gold> if you need assistance or information.</gray>
      <dark_gray><st>-----------------------------------------------------</st></dark_gray>""";

    public boolean isEnabled() {
        return enabled;
    }

    public String getMotd() {
        return motd;
    }
}
