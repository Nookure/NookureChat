package com.nookure.chat.paper.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.nookure.chat.Constants;
import com.nookure.chat.api.Logger;
import com.nookure.chat.api.NookureChatPlatform;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigMapper;
import com.nookure.chat.api.config.ConfigurationContainer;
import com.nookure.chat.api.config.FormatConfig;
import com.nookure.chat.paper.NookureChat;
import com.nookure.chat.paper.utils.PaperLoggerImpl;
import java.io.File;
import java.io.InputStream;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatBootstrapper extends JavaPlugin implements NookureChatPlatform<JavaPlugin> {
    private NookureChat plugin;
    private Logger logger;
    private boolean debug = false;
    private Injector injector;
    private ConfigurationContainer<Config> config;
    private ConfigurationContainer<FormatConfig> formatConfig;
    private final ConfigMapper configMapper = new ConfigMapper();

    @Override
    public void onEnable() {
        // Load plugin logger
        loadLogger();
        // Print plugin header
        Bukkit.getConsoleSender()
                .sendMessage(Component.text("▄             ▄ •▄ ▄• ▄▌▄▄▄  ▄▄▄ .     ▄▄·  ▄ .▄ ▄▄▄· ▄▄▄▄▄")
                        .color(NamedTextColor.RED));
        Bukkit.getConsoleSender()
                .sendMessage(Component.text("•█▌▐█▪     ▪     █▌▄▌▪█▪██▌▀▄ █·▀▄.▀·    ▐█ ▌▪██▪▐█▐█ ▀█ •██ \s")
                        .color(NamedTextColor.RED));
        Bukkit.getConsoleSender()
                .sendMessage(Component.text("▐█▐▐▌ ▄█▀▄  ▄█▀▄ ▐▀▀▄·█▌▐█▌▐▀▀▄ ▐▀▀▪▄    ██ ▄▄██▀▐█▄█▀▀█  ▐█.▪")
                        .color(NamedTextColor.RED));
        Bukkit.getConsoleSender()
                .sendMessage(Component.text("██▐█▌▐█▌.▐▌▐█▌.▐▌▐█.█▌▐█▄█▌▐█•█▌▐█▄▄▌    ▐███▌██▌▐▀▐█ ▪▐▌ ▐█▌·")
                        .color(NamedTextColor.RED));
        Bukkit.getConsoleSender()
                .sendMessage(Component.text("▀▀ █▪ ▀█▄▀▪ ▀█▄▀▪·▀  ▀ ▀▀▀ .▀  ▀ ▀▀▀     ·▀▀▀ ▀▀▀ · ▀  ▀  ▀▀▀\s")
                        .color(NamedTextColor.RED));
        Bukkit.getConsoleSender()
                .sendMessage(Component.text("NookureChat v" + Constants.VERSION + " by Lunna")
                        .color(NamedTextColor.RED));
        // Load Google Guice injector
        loadInjector();
        // Load configs
        loadConfig();
        // Inject the main class of the plugin
        injectPlugin();
        // Do the plugin enable
        plugin.onEnable();
        logger.info("Plugin enabled!");
    }

    public void loadConfig() {
        config = injector.getInstance(new Key<>() {});
        formatConfig = injector.getInstance(new Key<>() {});
        setDebug(config.get().isDebug());
    }

    @Override
    public void reload() {
        plugin.onReload();
    }

    @Override
    public void onDisable() {
        logger.info("Disabling plugin...");
        plugin.onDisable();
        logger.info("Plugin disabled!");
    }

    public void loadLogger() {
        logger = new PaperLoggerImpl(this);
    }

    public void injectPlugin() {
        logger.info("💉  Injecting plugin...");
        plugin = injector.getInstance(NookureChat.class);
        logger.info("💉  Plugin injected!");
    }

    public void loadInjector() {
        injector = Guice.createInjector(new PluginModule(this));
    }

    @Override
    public Logger getPLogger() {
        return logger;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public File getPluginDataFolder() {
        return getDataFolder();
    }

    @Override
    public InputStream getPluginResource(String s) {
        return getResource(s);
    }

    @Override
    public Injector getInjector() {
        return injector;
    }

    @Override
    public JavaPlugin getPlatform() {
        return this;
    }

    public ConfigMapper getConfigMapper() {
        return configMapper;
    }

    public ConfigurationContainer<Config> getPluginConfig() {
        return config != null ? config : injector.getInstance(new Key<>() {});
    }

    public ConfigurationContainer<FormatConfig> getFormatConfig() {
        return formatConfig != null ? formatConfig : injector.getInstance(new Key<>() {});
    }

    public static ChatBootstrapper getPlugin() {
        return getPlugin(ChatBootstrapper.class);
    }
}
