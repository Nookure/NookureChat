package com.nookure.chat.paper.filter;

import static com.nookure.chat.paper.utils.MessageUtils.sendMessage;

import com.google.inject.Inject;
import com.nookure.chat.api.chat.ChatFilter;
import com.nookure.chat.api.chat.ChatFilterData;
import com.nookure.chat.api.chat.ChatFilterPriority;
import com.nookure.chat.api.config.Config;
import com.nookure.chat.api.config.ConfigurationContainer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@ChatFilterData(
        name = "repeatedMessage",
        permission = "nookurechat.bypass.filter.repeatedMessage",
        priority = ChatFilterPriority.FIRST)
public class RepeatedMessageFilter extends ChatFilter {
    @Inject
    private ConfigurationContainer<Config> config;

    private final Map<UUID, List<Message>> lastMessageMap = new ConcurrentHashMap<>();

    @Override
    public boolean check(@NotNull Player player, @NotNull String message) {
        if (!lastMessageMap.containsKey(player.getUniqueId())) {
            lastMessageMap.put(player.getUniqueId(), List.of(new Message(message, System.currentTimeMillis())));
            return true;
        }

        List<Message> lastMessages = new ArrayList<>(lastMessageMap.get(player.getUniqueId()));

        lastMessages.removeIf(m -> System.currentTimeMillis() - m.time()
                > config.get().filters.repeatedMessage.getTimeRestriction() * 1000L);

        for (Message m : lastMessages) {
            if (m.message().equals(message)) {
                sendMessage(
                        player,
                        config.get()
                                .filters
                                .repeatedMessage
                                .getDenyMessage()
                                .replace(
                                        "{time}",
                                        String.valueOf(config.get()
                                                        .filters
                                                        .repeatedMessage
                                                        .getTimeRestriction()
                                                - (System.currentTimeMillis() - m.time()) / 1000L)));
                return false;
            }
        }

        lastMessages.add(new Message(message, System.currentTimeMillis()));
        lastMessageMap.put(player.getUniqueId(), lastMessages);

        return true;
    }

    private record Message(String message, long time) {
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof String) {
                return message.equals(obj);
            } else if (obj instanceof Message) {
                return message.equals(((Message) obj).message());
            } else {
                return false;
            }
        }
    }
}
