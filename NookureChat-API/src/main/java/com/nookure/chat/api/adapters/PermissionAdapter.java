package com.nookure.chat.api.adapters;

import org.bukkit.entity.Player;

public abstract class PermissionAdapter {
    public abstract String getHighestGroup(Player player);
}
