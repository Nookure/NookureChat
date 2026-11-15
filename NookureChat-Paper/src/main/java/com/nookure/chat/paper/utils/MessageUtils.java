package com.nookure.chat.paper.utils;

import com.nookure.chat.api.TextUtils;
import com.nookure.chat.paper.bootstrap.ChatBootstrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class MessageUtils {
    public static Component cmp(String message) {
        if (message == null || message.isEmpty()) {
            return Component.empty();
        }
        String prefix = ChatBootstrapper.getPlugin().getPluginConfig().get().getPrefix();
        message = message.replace("{prefix}", prefix);
        return MiniMessage.miniMessage().deserialize(TextUtils.toMM(message));
    }

    public static void sendMessage(CommandSender sender, String message) {
        if (sender == null || message == null || message.isEmpty()) {
            return;
        }
        sender.sendMessage(cmp(message));
    }

    public static void broadcastMessage(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        Component component = cmp(message);
        for (org.bukkit.entity.Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
            player.sendMessage(component);
        }
        org.bukkit.Bukkit.getConsoleSender().sendMessage(component);
    }
}
