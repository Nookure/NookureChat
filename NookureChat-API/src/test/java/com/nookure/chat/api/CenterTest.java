package com.nookure.chat.api;

import com.nookure.chat.api.font.DefaultFontInfo;
import org.junit.jupiter.api.Test;

public class CenterTest {
    @Test
    public void test() {
        String input = "<red>Hello <bold>World!</bold></red>";

        String centered = DefaultFontInfo.center(input);

        assert centered.equals("                             <red>Hello <bold>World!</bold></red>");
    }

    @Test
    public void testFullWidthHyphens() {
        String bar = "<dark_gray><st>-----------------------------------------------------</st></dark_gray>";
        String centered = DefaultFontInfo.center(bar);
        // 53 hyphens fill the 320px chat width, so center should not add leading spaces
        assert centered.equals(bar);
    }

    @Test
    public void testNestedHoverTags() {
        String text = "<hover:show_text:'<red>Tooltip</red>'>Visible Text</hover>";
        String stripped = DefaultFontInfo.removeTags(text);
        assert stripped.equals("Visible Text");
    }

    @Test
    public void testCenterIfContains() {
        String input = "{center}<red>Hello</red>";
        String centered = DefaultFontInfo.centerIfContains(input);
        assert !centered.contains("{center}");
        assert centered.startsWith(" ");
    }
}
