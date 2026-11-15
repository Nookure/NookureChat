package com.nookure.chat.api;

public interface NookureChatPlatform<P> extends NookureChat {
    P getPlatform();
}
