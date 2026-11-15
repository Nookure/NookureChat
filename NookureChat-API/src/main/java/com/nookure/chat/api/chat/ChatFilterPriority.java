package com.nookure.chat.api.chat;

public enum ChatFilterPriority {
    FIRST(0),
    BEFORE_NORMAL(1),
    NORMAL(2),
    LAST(3),
    LATEST(4);

    private final int priority;

    ChatFilterPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
