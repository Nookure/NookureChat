package com.nookure.chat.paper.listeners;

import com.google.inject.Inject;
import com.nookure.chat.api.annotation.MuteChatActive;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import com.nookure.chat.paper.filter.MentionsFilter;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatCommandDecorateEvent;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PaperChatDecorateEvent extends CommonChatEvent implements Listener {
    @Inject
    @MuteChatActive
    private AtomicBoolean muteChatActive;

    @Inject
    public ConfigurationContainer<Config> config;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChatDecorate(AsyncChatDecorateEvent event) {
        if (event.player() == null || event instanceof AsyncChatCommandDecorateEvent) {
            return;
        }

        String rawText = PlainTextComponentSerializer.plainText().serialize(event.result());
        Component decorated = decorate(event.player(), rawText);
        event.result(decorated);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        String stripped = PlainTextComponentSerializer.plainText().serialize(event.originalMessage());

        if (muteChatActive.get() && !event.getPlayer().hasPermission("nookurechat.bypass.mutechat")) {
            event.setCancelled(true);
            com.nookure.chat.paper.utils.MessageUtils.sendMessage(event.getPlayer(), config.get().messages.youCantTalk);
            return;
        }

        if (!check(event.getPlayer(), stripped)) {
            event.setCancelled(true);
            return;
        }

        var mentionsFilter = filterManager.getFilter("mentions").orElse(null);
        if (mentionsFilter instanceof MentionsFilter filter) {
            filter.notifyMentions(event.getPlayer(), stripped);
        }

        event.renderer(ChatRenderer.viewerUnaware(
                (source, sourceDisplayName, message) -> format(source, sourceDisplayName, message)));
    }
}
