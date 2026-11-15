package com.nookure.chat.paper.filter;

import static com.nookure.chat.paper.utils.MessageUtils.sendMessage;

import com.google.inject.Inject;
import com.nookure.chat.api.chat.ChatFilter;
import com.nookure.chat.api.chat.ChatFilterData;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import java.util.Locale;
import java.util.regex.Pattern;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ChatFilterData(name = "spam", permission = "nookurechat.bypass.filter.spam")
public class SpamFilter extends ChatFilter {
    @Inject
    private ConfigurationContainer<Config> config;

    public static final Pattern IPV4_REGEX = Pattern.compile("\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b");
    public static final Pattern DOMAIN_REGEX = Pattern.compile(
            "(https?://(?:www\\.)?|//(?:mc|play)\\.)?[a-zA-Z0-9-]{2,}\\.[a-zA-Z0-9-]{2,}(?:\\.[a-zA-Z0-9-]{2,})?");

    @Override
    public boolean check(@NotNull Player player, @NotNull String message) {
        String processed = message.toLowerCase(Locale.ROOT);
        for (String whitelisted : config.get().filters.spam.getWhitelistedDomains()) {
            processed = processed.replace(whitelisted.toLowerCase(Locale.ROOT), "");
        }

        if (IPV4_REGEX.matcher(processed).find()
                || DOMAIN_REGEX.matcher(processed).find()) {
            sendMessage(player, config.get().filters.spam.getDenyMessage());
            return false;
        }

        return true;
    }
}
