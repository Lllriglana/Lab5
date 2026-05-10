package ITMO.Lab5.model;

import ITMO.Lab5.exceptions.ValidationException;
import ITMO.Lab5.util.Validators;

import java.util.Objects;

/**
 * Chapter information for a space marine.
 */
public final class Chapter {
    private final String name;
    private final String world;

    public Chapter(String name, String world) throws ValidationException {
        this.name = Validators.requireNotBlank(name, "chapter.name");
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }

    
    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", world='" + world + '\'' +
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
        Chapter chapter = (Chapter) o;
        return Objects.equals(name, chapter.name) && Objects.equals(world, chapter.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, world);
    }
}
