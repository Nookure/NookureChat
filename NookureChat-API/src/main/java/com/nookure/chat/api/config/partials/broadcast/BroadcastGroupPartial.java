package com.nookure.chat.api.config.partials.broadcast;

import java.util.List;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class BroadcastGroupPartial {
    @Setting
    private boolean enabled = true;

    @Setting
    private int interval = 60;

    @Setting
    private String header = "<dark_gray><st>-----------------------------------------------------</st></dark_gray>";

    @Setting
    private String footer = "<dark_gray><st>-----------------------------------------------------</st></dark_gray>";

    @Setting
    private String prefix = "<b><red>Broadcast</red></b> <dark_gray>»</dark_gray>";

    @Setting
    private SoundPartial sound = new SoundPartial();

    @Setting
    private List<String> messages = List.of(
            "{prefix} <gray>Join our official Discord community: <hover:show_text:'<gold><b>Click to join!</b></gold>'><click:open_url:'https://discord.gg/'><red><u>discord.gg/community</u></red></click></hover></gray>",
            """
      <gray>Did you spot a player breaking the rules?</gray>
      <gray>Report them on Discord: <hover:show_text:'<gold>Open Discord</gold>'><click:open_url:'https://discord.gg/'><red><u>discord.gg/reports</u></red></click></hover></gray>""");

    public boolean isEnabled() {
        return enabled;
    }

    public int getInterval() {
        return interval;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    public String getPrefix() {
        return prefix;
    }

    public SoundPartial getSound() {
        return sound;
    }

    public List<String> getMessages() {
        return messages;
    }
}
