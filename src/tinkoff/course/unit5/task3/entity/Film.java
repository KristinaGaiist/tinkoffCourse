package unit5.task3.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Film implements Serializable {

    private final List<Actor> actors;
    private String name;
    private Date releaseDate;

    public Film(String name, Date releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.actors = new ArrayList<>();
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Actor> getActors() {
        return actors;
    }

    @Override
    public String toString() {
        return name + " " + new SimpleDateFormat("yyyy-MM-dd").format(releaseDate) + " " + actors.toString() + "\n";
    }
}
