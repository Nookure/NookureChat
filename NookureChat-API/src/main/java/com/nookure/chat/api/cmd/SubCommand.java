package com.nookure.chat.api.cmd;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class SubCommand {
    public abstract void execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] strings);
}
