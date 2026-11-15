package com.nookure.chat.api.config.partials.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class Messages {
    @Setting
    public String youHaveEnabledMuteChat =
            "{prefix} <green>✔</green> <gray>You have <red>muted</red> the global chat.</gray>";

    @Setting
    public String youHaveDisabledMuteChat =
            "{prefix} <green>✔</green> <gray>You have <green>unmuted</green> the global chat.</gray>";

    @Setting
    public String youCantTalk =
            "{prefix} <red>✕</red> <gray>You cannot talk while the chat is <red>muted</red>!</gray>";

    @Setting
    public String theChatHasBeenMuted =
            "{prefix} <red><b>Attention!</b></red> <gray>Global chat has been <red>muted</red>.</gray>";

    @Setting
    public String theChatHasBeenUnmuted =
            "{prefix} <green><b>Attention!</b></green> <gray>Global chat has been <green>unmuted</green>.</gray>";
}
