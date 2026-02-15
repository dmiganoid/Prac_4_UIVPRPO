import java.util.regex.Pattern;

public final class Patterns {
    private Patterns() {}

    /**
     * Телефоны:
     * +7 999 123-45-67
     * 8 (999) 123 45 67
     * +358 40 123 4567
     * 123-45-67 (редко, но встречается)
     */
    public static final Pattern PHONE = Pattern.compile(
            "(?<!\\w)(?:\\+\\d{1,3}[\\s-]?)?(?:\\(?\\d{2,4}\\)?[\\s-]?)?\\d{2,4}[\\s-]?\\d{2,4}[\\s-]?\\d{2,4}(?!\\w)"
    );

    /**
     * Координаты:
     * 59.934280, 30.335099
     * 59,934280 30,335099
     */
    public static final Pattern COORDINATES = Pattern.compile(
            "(?<!\\w)(-?\\d{1,2}[\\.,]\\d{3,}|-?\\d{1,3}[\\.,]\\d{3,})\\s*[, ]\\s*(-?\\d{1,2}[\\.,]\\d{3,}|-?\\d{1,3}[\\.,]\\d{3,})(?!\\w)"
    );

    /**
     * Ссылки на карты / гео:
     * Google Maps, Yandex Maps, 2GIS, OpenStreetMap, geo:, plus.codes
     */
    public static final Pattern MAP_LINKS = Pattern.compile(
            "(?i)\\b(?:https?://)?(?:www\\.)?(?:maps\\.google\\.com|goo\\.gl/maps|yandex\\.(?:ru|com)/maps|2gis\\.(?:ru|com)|openstreetmap\\.org|plus\\.codes)\\S*|\\bgeo:\\S+"
    );

    /**
     * Адресоподобные штуки (простая эвристика):
     * "ул. Пушкина, д. 10, кв. 5"
     * "проспект Ленина 12"
     * "дом 7, квартира 21"
     */
    public static final Pattern ADDRESS_LIKE = Pattern.compile(
            "(?i)\\b(?:ул\\.?|улица|пр-?т\\.?|проспект|пер\\.?|переулок|шоссе|наб\\.?|набережная|площадь|бул\\.?|бульвар)\\s+[\\p{L}\\-]+(?:\\s+[\\p{L}\\-]+){0,3}\\s*,?\\s*(?:д\\.?|дом)\\s*\\d+\\w?(?:\\s*,?\\s*(?:кв\\.?|квартира)\\s*\\d+)?\\b"
    );

    /**
     * "Имя Фамилия": две подряд Capitalized-лексемы (кириллица/латиница),
     * длина 2..30 чтобы отсечь мусор.
     */
    public static final Pattern NAME_SURNAME_PAIR = Pattern.compile(
            "(?<![\\p{L}])(\\p{Lu}[\\p{L}]{1,29})\\s+(\\p{Lu}[\\p{L}]{1,29})(?![\\p{L}])"
    );

    /**
     * Обращения:
     * "Дорогой Иван", "Уважаемая Анна", "Здравствуйте, Сергей"
     * Группа 1 = слово обращения (оставим), имя заменим.
     */
    public static final Pattern ADDRESSING_NAME = Pattern.compile(
            "(?i)\\b(дорог(?:ой|ая)|уважаем(?:ый|ая)|здравствуйте|привет)\\s*,?\\s+(\\p{Lu}[\\p{L}]{1,29})\\b"
    );
}
