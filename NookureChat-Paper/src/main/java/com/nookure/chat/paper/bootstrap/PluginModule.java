package com.nookure.chat.paper.bootstrap;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.nookure.chat.api.Logger;
import com.nookure.chat.api.NookureChat;
import com.nookure.chat.api.adapters.LuckPermsPermissionAdaptar;
import com.nookure.chat.api.adapters.NoImplPermissionAdapter;
import com.nookure.chat.api.adapters.PermissionAdapter;
import com.nookure.chat.api.adapters.VaultPermissionAdapter;
import com.nookure.chat.api.annotation.MuteChatActive;
import com.nookure.chat.api.config.*;
import com.nookure.chat.api.managers.FilterManager;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginModule extends AbstractModule {
    private final ChatBootstrapper plugin;

    public PluginModule(ChatBootstrapper plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(JavaPlugin.class).toInstance(plugin);
        bind(Logger.class).toInstance(plugin.getPLogger());
        bind(ChatBootstrapper.class).toInstance(plugin);
        bind(NookureChat.class).toInstance(plugin);
        bind(ConfigMapper.class).toInstance(plugin.getConfigMapper());
        bind(FilterManager.class).asEagerSingleton();
        bind(PermissionAdapter.class).toInstance(getPermissionAdapter());
        bind(CommandMap.class).toInstance(getCommandMap());

        bind(AtomicBoolean.class).annotatedWith(MuteChatActive.class).toInstance(new AtomicBoolean(false));

        try {
            bind(new TypeLiteral<ConfigurationContainer<Config>>() {}).toInstance(loadConfig());
            bind(new TypeLiteral<ConfigurationContainer<FormatConfig>>() {}).toInstance(loadFormatConfig());
            bind(new TypeLiteral<ConfigurationContainer<BannedWordsConfig>>() {})
                    .toInstance(loadBannedWordsConfig());
            bind(new TypeLiteral<ConfigurationContainer<AutoBroadcastConfig>>() {})
                    .toInstance(loadAutoBroadcastConfig());
            bind(new TypeLiteral<ConfigurationContainer<JoinMotdConfig>>() {}).toInstance(loadJoinMotdConfig());
        } catch (IOException e) {
            plugin.getPLogger().severe("Could not load config");
            throw new RuntimeException(e);
        }
    }

    public PermissionAdapter getPermissionAdapter() {
        try {
            Class.forName("net.luckperms.api.LuckPerms");
            return new LuckPermsPermissionAdaptar();
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("net.milkbowl.vault.permission.Permission");
                return new VaultPermissionAdapter();
            } catch (ClassNotFoundException ignored) {
                return new NoImplPermissionAdapter();
            }
        }
    }

    private CommandMap getCommandMap() {
        return Bukkit.getCommandMap();
    }

    private ConfigurationContainer<Config> loadConfig() throws IOException {
        ConfigurationContainer<Config> config =
                ConfigurationContainer.load(plugin.getDataFolder().toPath(), Config.class);
        plugin.setDebug(config.get().isDebug());
        return config;
    }

    private ConfigurationContainer<FormatConfig> loadFormatConfig() throws IOException {
        return ConfigurationContainer.load(plugin.getDataFolder().toPath(), FormatConfig.class, "format.yml");
    }

    private ConfigurationContainer<BannedWordsConfig> loadBannedWordsConfig() throws IOException {
        return ConfigurationContainer.load(plugin.getDataFolder().toPath(), BannedWordsConfig.class, "bannedWords.yml");
    }

    private ConfigurationContainer<AutoBroadcastConfig> loadAutoBroadcastConfig() throws IOException {
        return ConfigurationContainer.load(plugin.getDataFolder().toPath(), AutoBroadcastConfig.class, "broadcast.yml");
    }

    private ConfigurationContainer<JoinMotdConfig> loadJoinMotdConfig() throws IOException {
        return ConfigurationContainer.load(plugin.getDataFolder().toPath(), JoinMotdConfig.class, "joinMotd.yml");
    }
}
