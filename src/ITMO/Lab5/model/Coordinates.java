package ITMO.Lab5.model;

import ITMO.Lab5.exceptions.ValidationException;
import ITMO.Lab5.util.Validators;

import java.util.Objects;

/**
 * Coordinates of a space marine.
 */
public final class Coordinates {
    private final float x;
    private final Integer y;

    public Coordinates(float x, Integer y) throws ValidationException {
        this.x = Validators.requireLessOrEqual(x, 358.0f, "coordinates.x");
        this.y = Validators.requireNotNull(y, "coordinates.y");
    }

    public float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinates that = (Coordinates) o;
        return Float.compare(that.x, x) == 0 && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
