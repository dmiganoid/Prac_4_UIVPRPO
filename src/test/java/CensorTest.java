package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CensorTest {

    private final Censor censor = new Censor();

    @Test
    void censorsHttpLinks() {
        String in = "Смотри https://example.com/map?q=1 и http://test.ru/a/b.";
        String out = censor.censor(in);

        assertTrue(out.contains("[censored]"));
        assertFalse(out.contains("https://example.com"));
        assertFalse(out.contains("http://test.ru"));
    }

    @Test
    void censorsCoordinatesWithCommaOrDot() {
        String in1 = "Координаты: 59.9391, 30.3158";
        String out1 = censor.censor(in1);
        assertEquals("Координаты: [censored]", out1);

        String in2 = "GPS: -12,3456  98.7654";
        String out2 = censor.censor(in2);
        assertEquals("GPS: [censored]", out2);
    }


    @Test
    void censorsPhoneNumbers() {
        String in = "Позвони мне: +7 (999) 123-45-67 или 8 999 123 45 67.";
        String out = censor.censor(in);

        // должно заменить оба номера
        int count = countOccurrences(out, "[censored]");
        assertTrue(count >= 2, "Expected at least 2 censored tokens, got: " + count);

        assertFalse(out.contains("999"));
        assertFalse(out.contains("123-45-67"));
    }

    @Test
    void censorsNameSurnamePair() {
        String in = "Меня зовут Анна Петрова. Мы знакомы.";
        String out = censor.censor(in);

        assertEquals("Меня зовут [censored]. Мы знакомы.", out);
    }

    @Test
    void censorsAddressingNameButKeepsGreetingWord() {
        String in = "Дорогой Иван, спасибо за помощь!";
        String out = censor.censor(in);

        assertEquals("[censored], спасибо за помощь!", out);
    }


    private static int countOccurrences(String text, String token) {
        int count = 0;
        int idx = 0;
        while ((idx = text.indexOf(token, idx)) != -1) {
            count++;
            idx += token.length();
        }
        return count;
    }
}
