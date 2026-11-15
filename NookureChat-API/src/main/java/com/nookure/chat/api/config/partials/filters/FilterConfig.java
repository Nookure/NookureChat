package com.nookure.chat.api.config.partials.filters;

public abstract class FilterConfig {
    public abstract boolean isEnabled();

    public abstract String getDenyMessage();
}
