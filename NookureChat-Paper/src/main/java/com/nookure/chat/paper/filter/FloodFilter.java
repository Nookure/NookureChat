package com.nookure.chat.paper.filter;

import static com.nookure.chat.paper.utils.MessageUtils.sendMessage;

import com.google.inject.Inject;
import com.nookure.chat.api.chat.ChatFilter;
import com.nookure.chat.api.chat.ChatFilterData;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import java.util.regex.Pattern;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ChatFilterData(name = "flood", permission = "nookurechat.bypass.filter.flood")
public class FloodFilter extends ChatFilter {
    @Inject
    private ConfigurationContainer<Config> config;

    @Override
    public boolean check(@NotNull Player player, @NotNull String message) {
        Pattern matcher = Pattern.compile("(.)\\1{" + config.get().filters.flood.getMaxRepetitions() + ",}");

        if (matcher.matcher(message).find()) {
            sendMessage(player, config.get().filters.flood.getDenyMessage());
            return false;
        }

        return true;
    }
}
