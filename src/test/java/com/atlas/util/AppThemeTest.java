package com.atlas.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppThemeTest {

    @Test
    void separatorMatchesLineWidth() {
        assertEquals(AppTheme.LINE_WIDTH, AppTheme.separator().length());
    }

    @Test
    void centeredTextFillsTheLine() {
        assertEquals(AppTheme.LINE_WIDTH, AppTheme.center("ATLAS").length());
    }

    @Test
    void centeredTextIsBalanced() {
        String centered = AppTheme.center("ATLAS");
        int leftPadding = centered.indexOf("ATLAS");
        int rightPadding = centered.length() - leftPadding - "ATLAS".length();
        assertTrue(Math.abs(leftPadding - rightPadding) <= 1);
    }

    @Test
    void textWiderThanTheLineIsReturnedUntouched() {
        String longText = "x".repeat(AppTheme.LINE_WIDTH + 10);
        assertEquals(longText, AppTheme.center(longText));
    }
}
