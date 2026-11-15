package com.nookure.chat.api.chat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChatFilterData {
    String name();

    ChatFilterPriority priority() default ChatFilterPriority.NORMAL;

    String permission() default "";
}
