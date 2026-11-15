package com.nookure.chat.api.config.serializer;

import java.lang.reflect.Type;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

public class BukkitSoundSerializer implements TypeSerializer<Sound> {
    @Override
    public Sound deserialize(@NotNull Type type, @NotNull ConfigurationNode node) throws SerializationException {
        String value = node.getString();
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim().toLowerCase(java.util.Locale.ROOT);
        NamespacedKey key;
        if (normalized.contains(":")) {
            key = NamespacedKey.fromString(normalized);
        } else {
            key = NamespacedKey.minecraft(normalized.replace('_', '.'));
        }

        Sound sound = key != null ? Registry.SOUND_EVENT.get(key) : null;
        if (sound == null) {
            NamespacedKey fallback = NamespacedKey.minecraft(normalized.replace('.', '_'));
            sound = Registry.SOUND_EVENT.get(fallback);
        }

        return sound;
    }

    @Override
    public void serialize(@NotNull Type type, @Nullable Sound obj, @NotNull ConfigurationNode node)
            throws SerializationException {
        if (obj == null) {
            return;
        }

        NamespacedKey key = Registry.SOUND_EVENT.getKey(obj);
        if (key != null) {
            node.set(key.asString());
        }
    }
}
