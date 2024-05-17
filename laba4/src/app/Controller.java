
package app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Controller {
    @FXML
    public Button checkActorButton;
    @FXML
    public Button findCoActorsButton;
    @FXML
    public Button findMovieButton;
    @FXML
    public ChoiceBox <Model.Actor> actorComboBox;
    private Model.Database database;
    @FXML
    private void initialize() {
        // We create lists of actors
        Model.Actor actor1 = new Model.Actor("Actor_1", new ArrayList<>());
        Model.Actor actor2 = new Model.Actor("Actor_2", new ArrayList<>());
        Model.Actor actor3 = new Model.Actor("Actor_3", new ArrayList<>());
        Model.Actor actor4 = new Model.Actor("Actor_4", new ArrayList<>());
        Model.Actor actor5 = new Model.Actor("Actor_5", new ArrayList<>());
        // We create movies and add actors to them
        Model.Movie movie1 = new Model.Movie("Movie_1", List.of(actor1, actor2));
        Model.Movie movie2 = new Model.Movie("Movie_2", List.of(actor2, actor3, actor4));
        Model.Movie movie3 = new Model.Movie("Movie_3", List.of(actor2, actor3, actor1, actor5));
        // Add movies to the list
        List<Model.Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        // Add actors to the list
        List<Model.Actor> actors = new ArrayList<>();
        actors.add(actor1);
        actors.add(actor2);
        actors.add(actor3);
        actors.add(actor4);
        actors.add(actor5);
        // We initialize the database with movies and actors
        database = new Model.Database(movies, actors);
        // We initialize the combo box to display the actors
        // We configure how to display elements in the combo box
        actorComboBox.setItems(FXCollections.observableArrayList(actors));
        // We configure how to display elements in the combo box
        actorComboBox.setConverter(new StringConverter<Model.Actor>() {
            @Override
            public String toString(Model.Actor actor) {
                if (actor != null) {
                    return actor.getName(); // We return the name of the actor
                } else {
                    return ""; // We return an empty string if the actor is nul
                }
            }
            @Override
            public Model.Actor fromString(String string) {
                //  you also need to implement this method to use the combo box for editing
                return null;
            }
        });

    }

    // We call the findCoActors method when the button is pressed
    @FXML
    private void findCoActors() {
        // We get the selected actor from combo boxing
        Model.Actor selectedActor = actorComboBox.getValue();

        // We check whether the actor is selected
        if (selectedActor != null) {
            // If the actor is selected, we call the findCoActors method, passing the selected actor and the database
            List<Model.Actor> coActors = findCoActors(selectedActor, database);
            resultArea.setText("Co-Actors: " + coActors);
        } else {
            // If the actor is not selected, we display an error message
            resultArea.setText("Please select an actor.");
        }
    }
    @FXML
    private List<Model.Actor> findCoActors(Model.Actor actor, Model.Database database) {
        return database.getMovies().stream()                           // We create a Stream of films
                .filter(movie -> movie.getActors().contains(actor))    // We filter the films in which the given actor played
                .flatMap(movie -> movie.getActors().stream())          // Expand the list of actors in each film to a separate Stream
                .distinct()                                            // We remove duplicate actors
                .filter(coActor -> !coActor.equals(actor))             // We filter the actor himself
                .toList();                                             // Convert Stream to List
    }
    @FXML
    private TextArea resultArea;
    @FXML
    private void checkActor() {
        boolean hasActorWithNoMovies = hasActorWithNoMovies(database);
        resultArea.setText("Actor with no movies: " + hasActorWithNoMovies);
    }
    @FXML
    private void findMovie() {
        Model.Movie movieWithMostActors = findMovieWithMostActors(database);
        if (movieWithMostActors != null) {
            resultArea.setText("Movie with most actors: " + movieWithMostActors.getTitle());
        } else {
            resultArea.setText("No movie found with actors");
        }
    }
    private boolean hasActorWithNoMovies(Model.Database database) {
        return database.getActors().stream().anyMatch(actor -> actor.getMovies().isEmpty());
    }
    private Model.Movie findMovieWithMostActors(Model.Database database) {
        return database.getMovies().stream()
                .max(Comparator.comparingInt(movie -> movie.getActors().size()))
                .orElse(null);
    }
}
