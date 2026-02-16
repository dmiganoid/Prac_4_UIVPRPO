package main.java;

import java.util.regex.Pattern;

public final class Patterns {
    private Patterns() {}

    public static final Pattern MAP_LINKS = Pattern.compile(
            "(?iu)\\bhttps?://\\S+\\b"
    );

    public static final Pattern COORDINATES = Pattern.compile(
            "(?iu)(?<!\\w)-?\\d{1,3}[\\.,]\\d{3,}\\s*[, ]\\s*-?\\d{1,3}[\\.,]\\d{3,}(?!\\w)"
    );

    public static final Pattern PHONE = Pattern.compile(
            "(?iu)(?<!\\w)(?:\\+\\d{1,3}[\\s-]?)?(?:\\(?\\d{2,4}\\)?[\\s-]?)?\\d{2,4}[\\s-]?\\d{2,4}[\\s-]?\\d{2,4}(?!\\w)"
    );

    // ВАЖНО: ловит "по адресу ул. Пушкина, д. 10, кв. 5"
    public static final Pattern ADDRESS_LIKE = Pattern.compile(
            "(?iu)\\b(?:по\\s+адресу\\s*)?" +
                    "(?:ул\\.?|улица|пр-?т\\.?|проспект|пер\\.?|переулок|шоссе|наб\\.?|набережная|пл\\.?|площадь|бул\\.?|бульвар)\\s+" +
                    "[^\\n\\.]{1,80}?" +                  // до точки/перевода строки
                    "\\b(?:д\\.?|дом)\\s*\\d+\\w?" +
                    "(?:\\s*,\\s*(?:кв\\.?|квартира|оф\\.?|офис)\\s*\\d+)?"
    );

    // Пара "Имя Фамилия"
    public static final Pattern NAME_SURNAME_PAIR = Pattern.compile(
            "(?u)(?<![\\p{L}])(\\p{Lu}[\\p{L}]{1,29})\\s+(\\p{Lu}[\\p{L}]{1,29})(?![\\p{L}])"
    );

    // "Дорогой Иван" / "Дорогая Анна" / "Уважаемый Иван"
    public static final Pattern ADDRESSING_NAME = Pattern.compile(
            "(?iu)\\b(дорог(?:ой|ая)|уважаем(?:ый|ая))\\s+(\\p{Lu}[\\p{L}]{1,29})\\b"
    );

    // Подпись в ОДНУ строку: "С уважением, Анна"
    public static final Pattern SIGN_OFF_NAME = Pattern.compile(
            "(?iu)\\bс\\s+уважением\\s*,\\s*(\\p{Lu}[\\p{L}]{1,29})\\b"
    );


}
