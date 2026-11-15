package com.nookure.chat.paper.listeners;

import com.google.inject.Inject;
import com.nookure.chat.api.Logger;
import com.nookure.chat.api.TextUtils;
import com.nookure.chat.api.adapters.PermissionAdapter;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import com.nookure.chat.api.config.FormatConfig;
import com.nookure.chat.api.managers.FilterManager;
import com.nookure.chat.paper.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class CommonChatEvent {
    @Inject
    protected FilterManager filterManager;

    @Inject
    protected ConfigurationContainer<Config> config;

    @Inject
    protected ConfigurationContainer<FormatConfig> formatConfig;

    @Inject
    protected PermissionAdapter permissionAdapter;

    @Inject
    protected Logger logger;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean check(@NotNull Player player, @NotNull String message) {
        for (var filter : filterManager.getSortedFilters()) {
            String permission = filter.getFilterData().permission();
            if ((permission == null || permission.isEmpty() || !player.hasPermission(permission))
                    && !filter.check(player, message)) {
                return false;
            }
        }

        return true;
    }

    public Component decorate(@NotNull Player player, @NotNull String rawMessage) {
        String message = rawMessage;
        for (var filter : filterManager.getSortedFilters()) {
            if (filter.getFilterData().name().equals("mentions")) {
                // Mentions are formatted on the Component level to preserve formatting
                continue;
            }
            String permission = filter.getFilterData().permission();
            if (permission == null || permission.isEmpty() || !player.hasPermission(permission)) {
                message = filter.modify(player, message);
            }
        }

        Component messageComponent;
        if (player.hasPermission("nookurechat.color")) {
            messageComponent = TextUtils.CHAT_FORMATTER.deserialize(TextUtils.toMM(message));
        } else {
            messageComponent = Component.text(message);
        }

        if (config.get().filters.mentions.isEnabled()) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                    continue;
                }

                String targetName = onlinePlayer.getName();
                if (message.contains(targetName)) {
                    String replacementStr =
                            config.get().filters.mentions.getNameReplacement().replace("{player}", targetName);
                    Component replacementCmp = MessageUtils.cmp(replacementStr);
                    messageComponent = messageComponent.replaceText(
                            b -> b.matchLiteral(targetName).replacement(replacementCmp));
                }
            }
        }

        return messageComponent;
    }

    public Component format(
            @NotNull Player player, @NotNull Component sourceDisplayName, @NotNull Component messageComponent) {
        String group = permissionAdapter.getHighestGroup(player);

        String prefix = formatConfig.get().getDefaultPrefix();
        String suffix = formatConfig.get().getDefaultSuffix();
        String format = formatConfig.get().getDefaultFormat();

        logger.debug("User %s with group %s formatting message", player.getName(), group);

        if (formatConfig.get().getGroups().containsKey(group)) {
            FormatConfig.Group groupConfig = formatConfig.get().getGroups().get(group);
            prefix = groupConfig.getPrefix();
            suffix = groupConfig.getSuffix();
            format = groupConfig.getFormat().replace("{default}", format);
        }

        if (formatConfig.get().hoverFormat.isEnabled()) {
            String text = TextUtils.processPlaceholders(
                    player, String.join("\n", formatConfig.get().hoverFormat.getLines()));

            format = format.replace("{displayname}", "<hover:show_text:'" + text + "'>{displayname}</hover>");
            format = format.replace("{hover_start}", "<hover:show_text:'" + text + "'>");
            format = format.replace("{hover_end}", "</hover>");
        }

        format = format.replace("{prefix}", prefix).replace("{suffix}", suffix);
        format = TextUtils.toMM(TextUtils.processPlaceholders(player, format));

        Component formatted = MiniMessage.miniMessage().deserialize(format);

        formatted = formatted.replaceText(builder -> {
            builder.matchLiteral("{displayname}");
            builder.replacement(sourceDisplayName);
        });

        formatted = formatted.replaceText(builder -> {
            builder.matchLiteral("{message}");
            builder.replacement(messageComponent);
        });

        return formatted;
    }
}
