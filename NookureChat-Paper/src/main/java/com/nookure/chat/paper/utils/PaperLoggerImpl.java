package com.nookure.chat.paper.utils;

import com.nookure.chat.api.Logger;
import com.nookure.chat.paper.bootstrap.ChatBootstrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

public class PaperLoggerImpl implements Logger {
    private final ChatBootstrapper plugin;

    public PaperLoggerImpl(ChatBootstrapper plugin) {
        this.plugin = plugin;
    }

    @Override
    public void info(Component component) {
        Bukkit.getConsoleSender().sendMessage(getDefaultStyle(component, NamedTextColor.GRAY, "INFO"));
    }

    @Override
    public void warning(Component component) {
        Bukkit.getConsoleSender().sendMessage(getDefaultStyle(component, NamedTextColor.YELLOW, "WARN"));
    }

    @Override
    public void severe(Component component) {
        Bukkit.getConsoleSender().sendMessage(getDefaultStyle(component, NamedTextColor.RED, "SEVERE"));
    }

    @Override
    public void debug(Component component) {
        if (!plugin.isDebug()) return;
        Bukkit.getConsoleSender().sendMessage(getDefaultStyle(component, NamedTextColor.GRAY, "DEBUG"));
    }

    private Component getDefaultStyle(Component component, NamedTextColor color, String mode) {
        return Component.text("NookureChat")
                .color(NamedTextColor.RED)
                .append(Component.text(" | ").color(NamedTextColor.RED))
                .append(Component.text(mode).color(color))
                .append(Component.text(" > ").color(NamedTextColor.RED))
                .append(component);
    }
}
