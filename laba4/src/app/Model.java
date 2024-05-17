// Файл: Model.java
package app;

import java.util.List;

public class Model {
    public static class Movie {
        private String title;
        private List<Actor> actors;

        public Movie(String title, List<Actor> actors) {
            this.title = title;
            this.actors = actors;
        }

        public String getTitle() {
            return title;
        }

        public List<Actor> getActors() {
            return actors;
        }
    }

    public static class Actor {
        private String name;
        private List<Movie> movies;

        public Actor(String name, List<Movie> movies) {
            this.name = name;
            this.movies = movies;
        }

        public String getName() {
            return name;
        }

        public List<Movie> getMovies() {
            return movies;
        }
        @Override
        public String toString() {
            return name;
        }
    }

    public static class Database {
        private List<Movie> movies;
        private List<Actor> actors;

        public Database(List<Movie> movies, List<Actor> actors) {
            // Перевіряємо, чи список фільмів не є порожнім
            if (movies == null || movies.isEmpty()) {
                throw new IllegalArgumentException("Movies list cannot be null or empty");
            }
            // Перевіряємо, чи список акторів не є порожнім
            if (actors == null || actors.isEmpty()) {
                throw new IllegalArgumentException("Actors list cannot be null or empty");
            }
            this.movies = movies;
            this.actors = actors;
        }

        public List<Movie> getMovies() {
            return movies;
        }

        public List<Actor> getActors() {
            return actors;
        }
    }
}
