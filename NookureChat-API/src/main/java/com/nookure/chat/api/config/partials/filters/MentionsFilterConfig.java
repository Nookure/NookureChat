package com.nookure.chat.api.config.partials.filters;

import com.nookure.chat.api.config.partials.broadcast.SoundPartial;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class MentionsFilterConfig extends FilterConfig {
    @Setting
    private boolean enabled = true;

    @Setting
    private String title = "<red><b>Mentioned!</b></red>";

    @Setting
    private String subtitle = "<white>{player}</white> <gray>mentioned you in chat</gray>";

    @Setting
    private String nameReplacement = "<gold>@{player}</gold>";

    @Setting
    public SoundPartial sound = new SoundPartial();

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * This filter doesn't have a deny message.
     *
     * @return null
     */
    @Override
    public String getDenyMessage() {
        return null;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getNameReplacement() {
        return nameReplacement;
    }
}
