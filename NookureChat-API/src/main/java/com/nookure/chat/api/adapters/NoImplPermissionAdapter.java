package com.nookure.chat.api.adapters;

import org.bukkit.entity.Player;

public class NoImplPermissionAdapter extends PermissionAdapter {
    @Override
    public String getHighestGroup(Player player) {
        return "default";
    }
}
