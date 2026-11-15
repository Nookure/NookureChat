package com.nookure.chat.paper.cmd;

import static com.nookure.chat.paper.utils.MessageUtils.sendMessage;

import com.google.inject.Inject;
import com.nookure.chat.api.NookureChat;
import com.nookure.chat.api.cmd.SubCommand;
import com.nookure.chat.api.cmd.SubCommandData;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@SubCommandData(name = "reload", description = "Subcommand to reload the plugin", permission = "nookurechat.admin")
public class ReloadArg extends SubCommand {
    @Inject
    private NookureChat plugin;

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] strings) {
        plugin.reload();
        sendMessage(
                sender,
                "{prefix} <#74EE8A>✔</#74EE8A> <gray>Configuration has been <#74EE8A>reloaded successfully</#74EE8A>.</gray>");
    }
}
