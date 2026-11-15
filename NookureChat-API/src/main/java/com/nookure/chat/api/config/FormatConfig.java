package com.nookure.chat.api.config;

import com.nookure.chat.api.config.partials.format.HoverFormatConfig;
import java.util.Map;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class FormatConfig {
    @Setting
    private String defaultPrefix = "";

    @Setting
    private String defaultSuffix = "";

    @Setting
    private String defaultFormat = "{prefix} <reset>{displayname} <dark_gray>»</dark_gray> <white>{message}</white>";

    @Setting
    private String defaultJoinMessage =
            "<green><b>+</b></green> <gray><white>{displayname}</white> joined the server.</gray>";

    @Setting
    private String defaultQuitMessage =
            "<red><b>-</b></red> <gray><white>{displayname}</white> left the server.</gray>";

    @Setting
    private String defaultTitleJoinMessage = "<red><b>Welcome!</b></red>";

    @Setting
    private String defaultSubtitleJoinMessage = "<gray>Enjoy your stay on the server</gray>";

    @Setting
    private String firstJoinMessage =
            "<gold>★</gold> <gray>Welcome <gold><b>{player}</b></gold> to the server for the first time!</gray>";

    @Setting
    private boolean enableJoinQuitMessages = true;

    @Setting
    private boolean enableJoinTitles = true;

    @Setting
    public final HoverFormatConfig hoverFormat = new HoverFormatConfig();

    public String getDefaultJoinMessage() {
        return defaultJoinMessage;
    }

    public String getDefaultQuitMessage() {
        return defaultQuitMessage;
    }

    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    public String getDefaultFormat() {
        return defaultFormat;
    }

    public String getDefaultSuffix() {
        return defaultSuffix;
    }

    public boolean isEnableJoinQuitMessages() {
        return enableJoinQuitMessages;
    }

    public boolean isEnableJoinTitles() {
        return enableJoinTitles;
    }

    public String getDefaultTitleJoinMessage() {
        return defaultTitleJoinMessage;
    }

    public String getDefaultSubtitleJoinMessage() {
        return defaultSubtitleJoinMessage;
    }

    public String getFirstJoinMessage() {
        return firstJoinMessage;
    }

    @Setting
    private Map<String, Group> groups =
            Map.of("helper", new Group(), "mod", new Group(), "admin", new Group(), "owner", new Group());

    public Map<String, Group> getGroups() {
        return groups;
    }

    @ConfigSerializable
    public static final class Group {
        public Group() {}

        @Setting
        private String prefix = "";

        @Setting
        private String suffix = "";

        @Setting
        private String format = "{default}";

        @Setting
        private String joinMessage = "{default}";

        @Setting
        private String quitMessage = "{default}";

        @Setting
        private String titleJoinMessage = "{default}";

        @Setting
        private String subtitleJoinMessage = "{default}";

        public String getSuffix() {
            return suffix;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getFormat() {
            return format;
        }

        public String getJoinMessage() {
            return joinMessage;
        }

        public String getQuitMessage() {
            return quitMessage;
        }

        public String getTitleJoinMessage() {
            return titleJoinMessage;
        }

        public String getSubtitleJoinMessage() {
            return subtitleJoinMessage;
        }

        @Override
        public String toString() {
            return "Group{" + "prefix='"
                    + prefix + '\'' + ", suffix='"
                    + suffix + '\'' + ", format='"
                    + format + '\'' + ", joinMessage='"
                    + joinMessage + '\'' + ", quitMessage='"
                    + quitMessage + '\'' + ", titleJoinMessage='"
                    + titleJoinMessage + '\'' + ", subtitleJoinMessage='"
                    + subtitleJoinMessage + '\'' + '}';
        }
    }
}
