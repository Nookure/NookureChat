package com.nookure.chat.api.config;

import com.nookure.chat.api.config.partials.config.ClearChatPartial;
import com.nookure.chat.api.config.partials.config.Messages;
import com.nookure.chat.api.config.partials.filters.*;
import com.nookure.chat.api.config.partials.filters.BannedWordsConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class Config {
    @Setting
    private boolean debug = false;

    @Setting
    private String prefix = "<b><red>NookureChat</red></b> <dark_gray>»</dark_gray>";

    @Setting
    public Filters filters = new Filters();

    @Setting
    public Messages messages = new Messages();

    @ConfigSerializable
    public static final class Filters {
        @Setting
        public FloodConfig flood = new FloodConfig();

        @Setting
        public SpamConfig spam = new SpamConfig();

        @Setting
        public MessageSpamConfig messageSpam = new MessageSpamConfig();

        @Setting
        public BannedWordsConfig bannedWords = new BannedWordsConfig();

        @Setting
        public MentionsFilterConfig mentions = new MentionsFilterConfig();

        @Setting
        public RepeatedMessageConfig repeatedMessage = new RepeatedMessageConfig();

        @Setting
        public CapitalizationFilterConfig capitalization = new CapitalizationFilterConfig();
    }

    @Setting
    public ClearChatPartial clearChat = new ClearChatPartial();

    public String getPrefix() {
        return prefix;
    }

    public boolean isDebug() {
        return debug;
    }
}
