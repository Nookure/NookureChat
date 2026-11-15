package com.nookure.chat.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nookure.chat.api.Logger;
import com.nookure.chat.api.config.*;
import com.nookure.chat.api.managers.FilterManager;
import com.nookure.chat.paper.bootstrap.ChatBootstrapper;
import com.nookure.chat.paper.cmd.ClearChatCMD;
import com.nookure.chat.paper.cmd.MuteChatCMD;
import com.nookure.chat.paper.cmd.NookureChatCMD;
import com.nookure.chat.paper.filter.*;
import com.nookure.chat.paper.listeners.PaperChatDecorateEvent;
import com.nookure.chat.paper.listeners.PlayerJoinLeaveEvent;
import com.nookure.chat.paper.tasks.BroadcastTask;
import java.util.ArrayList;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

public class NookureChat {
    private final ArrayList<Listener> listeners = new ArrayList<>();
    private BukkitTask broadcastTask;

    @Inject
    private FilterManager filterManager;

    @Inject
    private ChatBootstrapper plugin;

    @Inject
    private Injector injector;

    @Inject
    private ConfigurationContainer<Config> config;

    @Inject
    private ConfigurationContainer<FormatConfig> formatConfig;

    @Inject
    private ConfigurationContainer<BannedWordsConfig> bannedWordsConfig;

    @Inject
    private ConfigurationContainer<AutoBroadcastConfig> broadcastConfig;

    @Inject
    private ConfigurationContainer<JoinMotdConfig> joinMotdConfig;

    @Inject
    private CommandMap commandMap;

    @Inject
    private Logger logger;

    public void onEnable() {
        new Metrics(plugin, 21151);
        loadListeners();
        registerFilters();
        loadCommands();
        loadTasks();
    }

    public void onDisable() {
        if (broadcastTask != null && !broadcastTask.isCancelled()) {
            broadcastTask.cancel();
            broadcastTask = null;
        }
        listeners.forEach(HandlerList::unregisterAll);
        listeners.clear();
        filterManager.clear();
    }

    public void onReload() {
        // Unregister all listeners
        listeners.forEach(HandlerList::unregisterAll);
        listeners.clear();

        reloadConfig();
        plugin.setDebug(config.get().isDebug());

        filterManager.clear();
        registerFilters();

        loadListeners();
        loadTasks();
    }

    public void loadListeners() {
        logger.debug("Loading listeners...");
        registerListener(PaperChatDecorateEvent.class);

        if (formatConfig.get().isEnableJoinQuitMessages()
                || joinMotdConfig.get().isEnabled()) {
            registerListener(PlayerJoinLeaveEvent.class);
        }
    }

    public void loadTasks() {
        logger.debug("Loading tasks...");
        if (broadcastTask != null && !broadcastTask.isCancelled()) {
            broadcastTask.cancel();
            broadcastTask = null;
        }
        if (broadcastConfig.get().isEnabled()) {
            broadcastTask = Bukkit.getScheduler()
                    .runTaskTimerAsynchronously(plugin, injector.getInstance(BroadcastTask.class), 0, 20);
        }
    }

    public void reloadConfig() {
        config.reload().join();
        formatConfig.reload().join();
        bannedWordsConfig.reload().join();
        broadcastConfig.reload().join();
        joinMotdConfig.reload().join();
    }

    public void loadCommands() {
        logger.debug("Loading commands...");
        commandMap.register("nchat", injector.getInstance(NookureChatCMD.class));
        commandMap.register("nchat", injector.getInstance(MuteChatCMD.class));
        commandMap.register("nchat", injector.getInstance(ClearChatCMD.class));
    }

    public void registerFilters() {
        filterManager.registerFilter(injector.getInstance(FloodFilter.class), config.get().filters.flood);
        filterManager.registerFilter(injector.getInstance(SpamFilter.class), config.get().filters.spam);
        filterManager.registerFilter(injector.getInstance(MessageSpamFilter.class), config.get().filters.messageSpam);
        filterManager.registerFilter(injector.getInstance(BannedWordsFilter.class), config.get().filters.bannedWords);
        filterManager.registerFilter(injector.getInstance(MentionsFilter.class), config.get().filters.mentions);
        filterManager.registerFilter(
                injector.getInstance(RepeatedMessageFilter.class), config.get().filters.repeatedMessage);
        filterManager.registerFilter(
                injector.getInstance(CapitalizationFilter.class), config.get().filters.capitalization);
    }

    public void registerListener(Class<? extends Listener> listener) {
        Listener listenerObj = injector.getInstance(listener);
        listeners.add(listenerObj);

        Bukkit.getPluginManager().registerEvents(listenerObj, plugin);
    }
}
