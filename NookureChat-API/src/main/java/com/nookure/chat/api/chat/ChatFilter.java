package com.nookure.chat.api.chat;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The ChatFilter class is used to filter the chat.
 * This class must have the ChatFilterData annotation
 * The ChatFilterData annotation is used to specify the name of the filter,
 * the priority of the filter, and the bypass permission of the filter.
 */
public abstract class ChatFilter {
  /**
   * Check if the message is valid
   * if the message is not valid, return false,
   * and the message will not be sent to the
   * other players.
   *
   * @param player  The player who sent the message
   * @param message The message to check
   * @return true if the message is valid, false if the message is invalid
   */
  abstract public boolean check(@NotNull Player player, @NotNull String message);

  /**
   * Modify the message before it is sent to the other players
   * @param player The player who sent the message
   * @param message The original message
   * @return The modified component
   */
  public String modify(@NotNull Player player, @NotNull String message) {
    return message;
  }

  /**
   * Get the name of the filter
   *
   * @return The name of the filter
   */
  public ChatFilterData getFilterData() {
    return this.getClass().getAnnotation(ChatFilterData.class);
  }
}
