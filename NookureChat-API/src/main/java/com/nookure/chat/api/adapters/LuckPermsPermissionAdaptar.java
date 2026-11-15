package com.nookure.chat.api.adapters;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LuckPermsPermissionAdaptar extends PermissionAdapter {
    @Override
    public String getHighestGroup(Player player) {
        try {
            LuckPerms api;
            try {
                api = LuckPermsProvider.get();
            } catch (IllegalStateException e) {
                RegisteredServiceProvider<LuckPerms> provider =
                        Bukkit.getServicesManager().getRegistration(LuckPerms.class);
                api = provider != null ? provider.getProvider() : null;
            }
            if (api == null) {
                return "default";
            }

            User user = api.getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                String group = user.getPrimaryGroup();
                return group != null ? group : "default";
            }
        } catch (Exception ignored) {
        }
        return "default";
    }
}
