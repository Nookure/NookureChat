package com.nookure.chat.paper.filter;

import com.google.inject.Inject;
import com.nookure.chat.api.chat.ChatFilter;
import com.nookure.chat.api.chat.ChatFilterData;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ChatFilterData(name = "capitalization")
public class CapitalizationFilter extends ChatFilter {
    @Inject
    private ConfigurationContainer<Config> config;

    @Override
    public boolean check(@NotNull Player player, @NotNull String message) {
        return true;
    }

    @Override
    public String modify(@NotNull Player player, @NotNull String message) {
        if (message.isBlank()) {
            return message;
        }

        message = Character.toUpperCase(message.charAt(0)) + message.substring(1);

        if (config.get().filters.capitalization.addDot()) {
            char lastChar = message.charAt(message.length() - 1);
            if (lastChar != '.' && lastChar != '!' && lastChar != '?') {
                message = message + ".";
            }
        }

        return message;
    }
}
