import java.util.Comparator;
import java.util.Objects;

/**
 * Immutable domain model representing a Tutorial.
 */
public final class Tutorial implements Comparable<Tutorial> {

    private final String name;
    private final int durationInMinutes;
    private final double rating;

    // -------------------- Constructors --------------------

    private Tutorial(Builder builder) {
        this.name = builder.name;
        this.durationInMinutes = builder.durationInMinutes;
        this.rating = builder.rating;
    }

    // -------------------- Getters --------------------

    public String getName() {
        return name;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public double getRating() {
        return rating;
    }

    // -------------------- Comparable (Default Sort by Name) --------------------

    @Override
    public int compareTo(Tutorial other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    // -------------------- Comparator Constants --------------------

    public static final Comparator<Tutorial> BY_RATING =
            Comparator.comparingDouble(Tutorial::getRating);

    public static final Comparator<Tutorial> BY_DURATION =
            Comparator.comparingInt(Tutorial::getDurationInMinutes);

    public static final Comparator<Tutorial> BY_NAME =
            Comparator.comparing(Tutorial::getName, String.CASE_INSENSITIVE_ORDER);

    // -------------------- equals & hashCode --------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tutorial)) return false;
        Tutorial tutorial = (Tutorial) o;
        return durationInMinutes == tutorial.durationInMinutes &&
               Double.compare(tutorial.rating, rating) == 0 &&
               Objects.equals(name, tutorial.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, durationInMinutes, rating);
    }

    // -------------------- toString --------------------

    @Override
    public String toString() {
        return String.format(
                "Tutorial[name='%s', duration=%d min, rating=%.1f]",
                name, durationInMinutes, rating
        );
    }

    // -------------------- Builder Pattern --------------------

    public static class Builder {

        private final String name;
        private int durationInMinutes = 0;
        private double rating = 0.0;

        public Builder(String name) {
            this.name = Objects.requireNonNull(name, "Name must not be null");
        }

        public Builder duration(int durationInMinutes) {
            if (durationInMinutes < 0) {
                throw new IllegalArgumentException("Duration cannot be negative");
            }
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public Builder rating(double rating) {
            if (rating < 0.0 || rating > 5.0) {
                throw new IllegalArgumentException("Rating must be between 0 and 5");
            }
            this.rating = rating;
            return this;
        }

        public Tutorial build() {
            return new Tutorial(this);
        }
    }
}
