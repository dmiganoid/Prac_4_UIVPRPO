package main.java;

import java.util.regex.Pattern;

public class Censor {
    private static final String TOKEN = "[censored]";

    public String censor(String text) {
        text = normalize(text);

        text = Patterns.MAP_LINKS.matcher(text).replaceAll(TOKEN);
        text = Patterns.COORDINATES.matcher(text).replaceAll(TOKEN);
        text = Patterns.ADDRESS_LIKE.matcher(text).replaceAll(TOKEN);
        text = Patterns.PHONE.matcher(text).replaceAll(TOKEN);

        // имена
        text = Patterns.NAME_SURNAME_PAIR.matcher(text).replaceAll(TOKEN);
        text = Patterns.ADDRESSING_NAME.matcher(text).replaceAll("$1 " + TOKEN);
        text = Patterns.SIGN_OFF_NAME.matcher(text).replaceAll("С уважением, " + TOKEN);

        return text;
    }

    private static String replaceAll(String text, Pattern pattern, String replacement) {
        return pattern.matcher(text).replaceAll(replacement);
    }

    /**
     * Эвристика для "Имя Фамилия" (две подряд слова с Заглавной, кириллица/латиница).
     * Чтобы меньше ложных срабатываний, добавляем контекстные триггеры обращения.
     */
    private static String censorNameSurnamePairs(String text) {
        // Вариант 1: любые пары "Имя Фамилия"
        String result = Patterns.NAME_SURNAME_PAIR.matcher(text).replaceAll(TOKEN);

        // Вариант 2: обращения "Дорогой Иван", "Уважаемая Анна" — тоже может быть имя
        result = Patterns.ADDRESSING_NAME.matcher(result).replaceAll("$1 " + TOKEN);

        return result;
    }

    private static String normalize(String s) {
        s = s.replace('\u00A0', ' ')
                .replace('\u202F', ' ')
                .replace('\u2007', ' ');
        s = s.replace("\r\n", "\n").replace("\r", "\n");
        s = s.replace('\t', ' ');
        return s;
    }

}
