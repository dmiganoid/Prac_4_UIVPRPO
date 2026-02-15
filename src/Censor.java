import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Censor {
    private static final String TOKEN = "[censored]";

    public String censor(String text) {
        // Порядок важен: сначала ссылки/координаты/телефоны, потом имена (чтобы не ломать контекст)
        text = replaceAll(text, Patterns.MAP_LINKS, TOKEN);
        text = replaceAll(text, Patterns.COORDINATES, TOKEN);
        text = replaceAll(text, Patterns.ADDRESS_LIKE, TOKEN);

        text = replaceAll(text, Patterns.PHONE, TOKEN);

        text = censorNameSurnamePairs(text);

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
}
