package com.nookure.chat.paper.filter;

import static com.nookure.chat.paper.utils.MessageUtils.sendMessage;

import com.google.inject.Inject;
import com.nookure.chat.api.chat.ChatFilter;
import com.nookure.chat.api.chat.ChatFilterData;
import com.nookure.chat.api.config.BannedWordsConfig;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import java.util.Locale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ChatFilterData(name = "bannedWords", permission = "nookurechat.bypass.filter.bannedWords")
public class BannedWordsFilter extends ChatFilter {
    @Inject
    private ConfigurationContainer<Config> config;

    @Inject
    private ConfigurationContainer<BannedWordsConfig> bannedWordsConfig;

    @Override
    public boolean check(@NotNull Player player, @NotNull String message) {
        String lowerMessage = message.toLowerCase(Locale.ROOT);
        String normalizedWhitespace = lowerMessage.replaceAll("\\s+", " ");
        String[] split = lowerMessage.split("\\s+");

        for (String rawWord : bannedWordsConfig.get().getWords()) {
            if (rawWord == null || rawWord.isBlank()) {
                continue;
            }

            String word = rawWord.toLowerCase(Locale.ROOT);
            if (word.contains(" ")) {
                if (normalizedWhitespace.contains(word)) {
                    sendMessage(player, config.get().filters.bannedWords.getDenyMessage());
                    return false;
                }
            } else {
                for (String s : split) {
                    String cleanToken = s.replaceAll("^[^\\p{L}\\p{N}]+|[^\\p{L}\\p{N}]+$", "");
                    if (s.equals(word) || cleanToken.equals(word)) {
                        sendMessage(player, config.get().filters.bannedWords.getDenyMessage());
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
