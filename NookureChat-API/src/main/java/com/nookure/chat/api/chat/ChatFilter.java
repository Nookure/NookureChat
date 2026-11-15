package com.nookure.chat.api.chat;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The ChatFilter class is used to filter the chat.
 * This class must have the ChatFilterData annotation
 * The ChatFilterData annotation is used to specify the name of the filter,
 * the priority of the filter, and the bypass permission of the filter.
 */
public abstract class ChatFilter implements Comparable<ChatFilter> {
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
    public abstract boolean check(@NotNull Player player, @NotNull String message);

    /**
     * Modify the message before it is sent to the other players
     *
     * @param player  The player who sent the message
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

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param toCompare the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(@NotNull ChatFilter toCompare) {
        return Integer.compare(
                getFilterData().priority().getPriority(),
                toCompare.getFilterData().priority().getPriority());
    }
}
