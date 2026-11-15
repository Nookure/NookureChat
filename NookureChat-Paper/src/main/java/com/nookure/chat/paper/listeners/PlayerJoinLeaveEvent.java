package com.nookure.chat.paper.listeners;

import static com.nookure.chat.paper.utils.MessageUtils.sendMessage;

import com.google.inject.Inject;
import com.nookure.chat.api.Logger;
import com.nookure.chat.api.TextUtils;
import com.nookure.chat.api.config.ConfigurationContainer;
import com.nookure.chat.api.config.FormatConfig;
import com.nookure.chat.api.config.JoinMotdConfig;
import com.nookure.chat.api.font.DefaultFontInfo;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeaveEvent extends CommonPlayerJoinLeaveEvent implements Listener {
    @Inject
    private ConfigurationContainer<FormatConfig> formatConfig;

    @Inject
    private ConfigurationContainer<JoinMotdConfig> joinMotdConfig;

    @Inject
    private Logger logger;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (formatConfig.get().isEnableJoinQuitMessages()) {
            CommonPlayerJoinLeaveEvent.Response format = format(event.getPlayer());

            if (!event.getPlayer().hasPlayedBefore()
                    && !PlainTextComponentSerializer.plainText()
                            .serialize(format.firstJoinMessage())
                            .isEmpty()) {
                event.joinMessage(format.firstJoinMessage());
            } else {
                event.joinMessage(format.joinMessage());
            }

            if (formatConfig.get().isEnableJoinTitles()) {
                Player player = event.getPlayer();

                Title title = Title.title(format.titleJoinMessage(), format.subtitleJoinMessage());

                logger.debug("Sending title to %s: %s", player.getName(), title);
                player.showTitle(title);
            }

            logger.debug(
                    "Player %s joined the server with join message %s",
                    event.getPlayer().getName(), format.joinMessage());
        }

        if (joinMotdConfig.get().isEnabled()) {
            for (String line : joinMotdConfig.get().getMotd().split("\n")) {
                sendMessage(
                        event.getPlayer(),
                        DefaultFontInfo.centerIfContains(TextUtils.processPlaceholders(event.getPlayer(), line)));
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (formatConfig.get().isEnableJoinQuitMessages()) {
            event.quitMessage(format(event.getPlayer()).quitMessage());
        }
    }
}
