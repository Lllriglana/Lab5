package ITMO.Lab5.input;

import ITMO.Lab5.exceptions.InputException;
import ITMO.Lab5.exceptions.ValidationException;
import ITMO.Lab5.model.AstartesCategory;
import ITMO.Lab5.model.Chapter;
import ITMO.Lab5.model.Coordinates;
import ITMO.Lab5.model.MeleeWeapon;
import ITMO.Lab5.model.SpaceMarine;
import ITMO.Lab5.model.Weapon;
import ITMO.Lab5.util.EnumUtils;
import ITMO.Lab5.util.Validators;

import java.io.PrintStream;
import java.time.ZonedDateTime;

/**
 * Reads and validates composite SpaceMarine input from current input source.
 */
public final class SpaceMarineInputReader {
    private final InputManager inputManager;
    private final PrintStream out;

    public SpaceMarineInputReader(InputManager inputManager, PrintStream out) {
        this.inputManager = inputManager;
        this.out = out;
    }

    public SpaceMarine readSpaceMarine(Integer id, ZonedDateTime creationDate) throws InputException, ValidationException {
        String name = readRequiredString("name (required)", "name");

        long x = readLongWithMax("coordinates.x (long, <= 358)", 358, "coordinates.x");
        Float y = readFloatRequired("coordinates.y (float, required)", "coordinates.y");

        int health = readPositiveInt("health (int, > 0)", "health");

        String categoryPrompt = "category (" + EnumUtils.allowedValues(AstartesCategory.class) + ", required)";
        AstartesCategory category = readEnum(categoryPrompt, AstartesCategory.class, false);

        String weaponPrompt = "weaponType (" + EnumUtils.allowedValues(Weapon.class) + ", empty for null)";
        Weapon weaponType = readEnum(weaponPrompt, Weapon.class, true);

        String meleePrompt = "meleeWeapon (" + EnumUtils.allowedValues(MeleeWeapon.class) + ", empty for null)";
        MeleeWeapon meleeWeapon = readEnum(meleePrompt, MeleeWeapon.class, true);

        String chapterName = readRequiredString("chapter.name (required)", "chapter.name");
        String chapterWorld = readNullableString("chapter.world (empty for null)");

        Coordinates coordinates = new Coordinates(x, y);
        Chapter chapter = new Chapter(chapterName, chapterWorld);

        return new SpaceMarine(
                id,
                name,
                coordinates,
                creationDate,
                health,
                category,
                weaponType,
                meleeWeapon,
                chapter
        );
    }

    private String readRequiredString(String prompt, String fieldName) throws InputException {
        return readUntilValid(prompt, raw -> Validators.requireNotBlank(raw == null ? null : raw.trim(), fieldName));
    }

    private String readNullableString(String prompt) throws InputException {
        return readUntilValid(prompt, raw -> {
            if (raw == null) {
                return null;
            }
            String trimmed = raw.trim();
            if (trimmed.isEmpty()) {
                return null;
            }
            return trimmed;
        });
    }

    private long readLongWithMax(String prompt, long max, String fieldName) throws InputException {
        return readUntilValid(prompt, raw -> {
            String trimmed = normalizeRequiredNumeric(raw, fieldName);
            long value;
            try {
                value = Long.parseLong(trimmed);
            } catch (NumberFormatException e) {
                throw new ValidationException(fieldName + " must be a long number");
            }
            Validators.requireLessOrEqual(value, max, fieldName);
            return value;
        });
    }

    private Float readFloatRequired(String prompt, String fieldName) throws InputException {
        return readUntilValid(prompt, raw -> {
            String trimmed = normalizeRequiredNumeric(raw, fieldName);
            try {
                return Float.parseFloat(trimmed);
            } catch (NumberFormatException e) {
                throw new ValidationException(fieldName + " must be a float number");
            }
        });
    }

    private int readPositiveInt(String prompt, String fieldName) throws InputException {
        return readUntilValid(prompt, raw -> {
            String trimmed = normalizeRequiredNumeric(raw, fieldName);
            int value;
            try {
                value = Integer.parseInt(trimmed);
            } catch (NumberFormatException e) {
                throw new ValidationException(fieldName + " must be an integer number");
            }
            Validators.requireGreaterThanZero(value, fieldName);
            return value;
        });
    }

    private <E extends Enum<E>> E readEnum(String prompt, Class<E> enumClass, boolean nullable) throws InputException {
        return readUntilValid(prompt, raw -> EnumUtils.parseEnum(enumClass, raw, nullable));
    }

    private String normalizeRequiredNumeric(String raw, String fieldName) throws ValidationException {
        if (raw == null || raw.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required");
        }
        return raw.trim();
    }

    private <T> T readUntilValid(String prompt, ValueParser<T> parser) throws InputException {
        while (true) {
            String raw = inputManager.readFieldLine(prompt);
            try {
                return parser.parse(raw);
            } catch (ValidationException e) {
                out.println("Input error: " + e.getMessage());
            }
        }
    }

    @FunctionalInterface
    private interface ValueParser<T> {
        T parse(String raw) throws ValidationException;
    }
}
