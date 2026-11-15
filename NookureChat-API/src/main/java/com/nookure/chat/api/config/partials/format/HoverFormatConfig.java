package com.nookure.chat.api.config.partials.format;

import java.util.List;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class HoverFormatConfig {
    @Setting
    private boolean enabled = true;

    @Setting
    private List<String> lines = List.of(
            "<b><red>%player_name%</red></b>",
            "<dark_gray><st>━━━━━━━━━━━━━━━━━━</st></dark_gray>",
            "<gray>Rank: <white>%vault_group%</white>",
            "<gray>Ping: <green>%player_ping%ms</green>",
            "",
            "<gold>✦ Click to mention</gold>");

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getLines() {
        return lines;
    }
}
