package com.nookure.chat.paper.cmd;

import com.google.inject.Inject;
import com.nookure.chat.api.annotation.MuteChatActive;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import com.nookure.chat.paper.utils.MessageUtils;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MuteChatCMD extends Command {
    private final AtomicBoolean muteChatActive;
    private final ConfigurationContainer<Config> config;

    @Inject
    public MuteChatCMD(
            @NotNull @MuteChatActive final AtomicBoolean muteChatActive,
            @NotNull final ConfigurationContainer<Config> config) {
        super("mutechat");
        setPermission("nookurechat.mutechat");
        setDescription("Mute the chat.");

        this.muteChatActive = muteChatActive;
        this.config = config;
    }

    @Override
    public boolean execute(
            @NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        if (!testPermission(sender)) return true;

        if (muteChatActive.get()) {
            muteChatActive.set(false);
            MessageUtils.sendMessage(sender, config.get().messages.youHaveDisabledMuteChat);
            MessageUtils.broadcastMessage(config.get().messages.theChatHasBeenUnmuted);
        } else {
            muteChatActive.set(true);
            MessageUtils.sendMessage(sender, config.get().messages.youHaveEnabledMuteChat);
            MessageUtils.broadcastMessage(config.get().messages.theChatHasBeenMuted);
        }

        return true;
    }
}
