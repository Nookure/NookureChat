package com.nookure.chat.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nookure.chat.api.config.BannedWordsConfig;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BannedWordsConfigTest {

    @Test
    public void testSpanishBannedWordsPresent() {
        BannedWordsConfig config = new BannedWordsConfig();
        List<String> words = config.getWords();

        assertFalse(words.isEmpty(), "Words list should not be empty");

        for (String word : words) {
            assertFalse(word.isBlank(), "No word should be blank");
        }

        assertTrue(words.contains("gilipollas"), "Should contain gilipollas");
        assertTrue(words.contains("capullo"), "Should contain capullo");
        assertTrue(words.contains("subnormal"), "Should contain subnormal");
        assertTrue(words.contains("cabron"), "Should contain cabron");
        assertTrue(words.contains("cabrón"), "Should contain cabrón");
        assertTrue(words.contains("coño"), "Should contain coño");
        assertTrue(words.contains("hostia"), "Should contain hostia");
        assertTrue(words.contains("polla"), "Should contain polla");
        assertTrue(words.contains("follar"), "Should contain follar");

        assertTrue(words.contains("pendejo"), "Should contain pendejo");
        assertTrue(words.contains("chingar"), "Should contain chingar");
        assertTrue(words.contains("pinche"), "Should contain pinche");
        assertTrue(words.contains("verga"), "Should contain verga");
        assertTrue(words.contains("culero"), "Should contain culero");

        assertTrue(words.contains("hijueputa"), "Should contain hijueputa");
        assertTrue(words.contains("gonorrea"), "Should contain gonorrea");
        assertTrue(words.contains("malparido"), "Should contain malparido");
        assertTrue(words.contains("mamahuevo"), "Should contain mamahuevo");

        assertTrue(words.contains("pelotudo"), "Should contain pelotudo");
        assertTrue(words.contains("boludo"), "Should contain boludo");
        assertTrue(words.contains("concha de tu madre"), "Should contain concha de tu madre");
        assertTrue(words.contains("conchetumadre"), "Should contain conchetumadre");
        assertTrue(words.contains("culiao"), "Should contain culiao");
        assertTrue(words.contains("weon"), "Should contain weon");
    }
}
