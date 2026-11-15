package com.nookure.chat.paper.filter;

import static com.nookure.chat.paper.utils.MessageUtils.cmp;

import com.google.inject.Inject;
import com.nookure.chat.api.chat.ChatFilter;
import com.nookure.chat.api.chat.ChatFilterData;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import com.nookure.chat.api.config.partials.filters.MentionsFilterConfig;
import java.util.concurrent.atomic.AtomicReference;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ChatFilterData(name = "mentions")
public class MentionsFilter extends ChatFilter {
    @Inject
    private ConfigurationContainer<Config> config;

    @Override
    public boolean check(@NotNull Player player, @NotNull String message) {
        return true;
    }

    @Override
    public String modify(@NotNull Player player, @NotNull String message) {
        AtomicReference<String> finalMessage = new AtomicReference<>(message);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                return;
            }

            if (message.contains(onlinePlayer.getName())) {
                finalMessage.set(finalMessage
                        .get()
                        .replace(
                                onlinePlayer.getName(),
                                config.get()
                                        .filters
                                        .mentions
                                        .getNameReplacement()
                                        .replace("{player}", onlinePlayer.getName())));
            }
        });

        return finalMessage.get();
    }

    public void notifyMentions(@NotNull Player player, @NotNull String message) {
        MentionsFilterConfig mentions = config.get().filters.mentions;
        if (!mentions.isEnabled()) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                return;
            }

            if (message.contains(onlinePlayer.getName())) {
                Title title = Title.title(
                        cmp(mentions.getTitle().replace("{player}", player.getName())),
                        cmp(mentions.getSubtitle().replace("{player}", player.getName())));

                onlinePlayer.showTitle(title);

                if (mentions.sound.isEnabled() && mentions.sound.getSound() != null) {
                    net.kyori.adventure.sound.Sound sound = net.kyori.adventure.sound.Sound.sound(
                            mentions.sound.getSound(),
                            net.kyori.adventure.sound.Sound.Source.MASTER,
                            mentions.sound.getVolume(),
                            mentions.sound.getPitch());
                    onlinePlayer.playSound(sound, net.kyori.adventure.sound.Sound.Emitter.self());
                }
            }
        });
    }
}
