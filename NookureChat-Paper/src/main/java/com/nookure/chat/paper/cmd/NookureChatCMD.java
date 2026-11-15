package com.nookure.chat.paper.cmd;

import static com.nookure.chat.paper.utils.MessageUtils.sendMessage;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.chat.Constants;
import com.nookure.chat.api.cmd.SubCommand;
import com.nookure.chat.api.cmd.SubCommandData;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NookureChatCMD extends Command {
    private final Injector injector;
    private final LinkedHashMap<String, SubCommandData> subCommandDataMap = new LinkedHashMap<>();
    private final HashMap<SubCommandData, SubCommand> subCommandMap = new HashMap<>();

    protected @Inject NookureChatCMD(Injector injector) {
        super("NookureChat");

        this.injector = injector;

        setAliases(List.of("nc", "nchat"));
        setDescription("NookureChat main command");
        setPermission("nookurechat.admin");

        registerSubCommand(ReloadArg.class);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (!testPermission(sender)) return true;

        if (args.length == 0) {
            help(sender, label);
            return true;
        }

        SubCommandData subCommandData = subCommandDataMap.getOrDefault(args[0], null);

        if (subCommandData == null) {
            help(sender, label);
            return true;
        }

        SubCommand subCommand = subCommandMap.getOrDefault(subCommandData, null);

        if (subCommand == null) {
            help(sender, label);
            return true;
        }

        if (!sender.hasPermission(subCommandData.permission())) {
            help(sender, label);
            return true;
        }

        subCommand.execute(sender, args[0], Arrays.stream(args).skip(1).toArray(String[]::new));

        return true;
    }

    public void help(CommandSender sender, String label) {
        sendMessage(
                sender,
                "<dark_gray><st>━━━━━━━━━━━━</st></dark_gray> <b><red>Nookure</red><white>Chat</white></b> <dark_gray>(v"
                        + Constants.VERSION + ")</dark_gray> <dark_gray><st>━━━━━━━━━━━━</st></dark_gray>");

        subCommandMap.forEach((data, subCommand) -> {
            if (sender instanceof Player && !sender.hasPermission(data.permission())) {
                return;
            }

            String command = label + " <white>" + data.name() + "</white>";
            String suggestedCommand = label + " " + data.name();

            sendMessage(
                    sender,
                    " <red>»</red> <white><hover:show_text:'<gray>Click to suggest <white>/" + suggestedCommand
                            + "</white>'><click:suggest_command:'/" + suggestedCommand + "'>/" + command
                            + "</click></hover></white> <dark_gray>-</dark_gray> <gray>" + data.description()
                            + "</gray>");
        });

        sendMessage(sender, "<dark_gray><st>-----------------------------------------------------</st></dark_gray>");
    }

    private void registerSubCommand(Class<? extends SubCommand> subCommandClass) {
        SubCommand subCommand = injector.getInstance(subCommandClass);

        SubCommandData subCommandData = subCommand.getClass().getAnnotation(SubCommandData.class);

        subCommandDataMap.put(subCommandData.name(), subCommandData);
        subCommandMap.put(subCommandData, subCommand);
    }
}
