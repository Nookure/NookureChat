package com.nookure.chat.paper.tasks;

import static com.nookure.chat.paper.utils.MessageUtils.sendMessage;

import com.google.inject.Inject;
import com.nookure.chat.api.TextUtils;
import com.nookure.chat.api.config.AutoBroadcastConfig;
import com.nookure.chat.api.config.ConfigurationContainer;
import com.nookure.chat.api.font.DefaultFontInfo;
import java.util.HashMap;
import org.bukkit.Bukkit;

public class BroadcastTask implements Runnable {
    @Inject
    private ConfigurationContainer<AutoBroadcastConfig> config;

    private final HashMap<String, Integer> currentMessages = new HashMap<>();
    private static final long maxTick = Long.MAX_VALUE - 1000;
    // One tick per second, not like minecraft ticks
    private long currentTick = 0;

    @Override
    public void run() {
        if (!config.get().isEnabled()) {
            return;
        }

        if (currentTick > maxTick) {
            currentTick = 0;
        }

        currentTick += 1;

        config.get().getBroadcasts().forEach((name, broadcast) -> {
            if (!broadcast.isEnabled()) {
                return;
            }

            int interval = broadcast.getInterval();
            if (interval <= 0 || currentTick % interval != 0) {
                return;
            }

            var messages = broadcast.getMessages();
            if (messages == null || messages.isEmpty()) {
                return;
            }

            int currentIndex = currentMessages.getOrDefault(name, 0);
            if (currentIndex >= messages.size()) {
                currentIndex = 0;
            }
            String templateMessage = messages.get(currentIndex);
            currentMessages.put(name, (currentIndex + 1) % messages.size());

            String prefix = broadcast.getPrefix();
            String processedTemplate = templateMessage.replace("{prefix}", prefix);
            String header = broadcast.getHeader();
            String footer = broadcast.getFooter();

            net.kyori.adventure.sound.Sound soundToPlay = null;
            if (broadcast.getSound().isEnabled() && broadcast.getSound().getSound() != null) {
                soundToPlay = net.kyori.adventure.sound.Sound.sound(
                        broadcast.getSound().getSound(),
                        net.kyori.adventure.sound.Sound.Source.MASTER,
                        broadcast.getSound().getVolume(),
                        broadcast.getSound().getPitch());
            }
            final net.kyori.adventure.sound.Sound finalSound = soundToPlay;

            Bukkit.getOnlinePlayers().forEach(player -> {
                if (!header.isEmpty()) {
                    sendMessage(player, DefaultFontInfo.center(header.replace("{center}", "")));
                }

                for (String s : processedTemplate.split("\n")) {
                    sendMessage(
                            player,
                            DefaultFontInfo.center(
                                    TextUtils.processPlaceholders(player, s).replace("{center}", "")));
                }

                if (!footer.isEmpty()) {
                    sendMessage(player, DefaultFontInfo.center(footer.replace("{center}", "")));
                }

                if (finalSound != null) {
                    player.playSound(finalSound, net.kyori.adventure.sound.Sound.Emitter.self());
                }
            });
        });
    }
}
