package com.nookure.chat.paper.cmd;

import static com.nookure.chat.paper.utils.MessageUtils.sendMessage;

import com.google.inject.Inject;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import com.nookure.chat.paper.utils.MessageUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClearChatCMD extends Command {
    @Inject
    private ConfigurationContainer<Config> config;

    protected ClearChatCMD() {
        super("clearchat", "Command to clear the chat", "/clearchat", List.of());
        setPermission("nookurechat.clearchat");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!testPermission(sender)) return true;
        if (!config.get().clearChat.isEnabled()) return true;

        CompletableFuture.runAsync(() -> {
                    Bukkit.getOnlinePlayers().forEach(player -> sendMessage(player, StringUtils.repeat(" \n", 1000)));
                })
                .thenAccept(aVoid -> sender.sendMessage(
                        MessageUtils.cmp(config.get().clearChat.getSuccessful())));

        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(
            @NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args)
            throws IllegalArgumentException {
        return List.of();
    }
}
