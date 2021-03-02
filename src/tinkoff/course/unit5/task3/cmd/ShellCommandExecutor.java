package unit5.task3.cmd;

import java.util.Arrays;
import unit5.task3.cmd.view.ExitView;
import unit5.task3.cmd.view.FilmView;
import unit5.task3.exception.CommandNotFoundException;

public final class ShellCommandExecutor {

    private final FilmView filmView;
    private final ExitView exitView;

    public ShellCommandExecutor(FilmView filmView, ExitView exitView) {
        this.filmView = filmView;
        this.exitView = exitView;
    }

    public void execute(String[] arg) {
        String commandName = arg[0].trim();
        String[] commandArs = Arrays.stream(arg).skip(1).filter(e -> !e.equals("")).toArray(String[]::new);

        switch (commandName) {
            case "show-film-collection" -> filmView.showFilmCollection();
            case "show-film-info" -> filmView.showFilmInfo(commandArs);
            case "show-film-actors" -> filmView.showFilmActors(commandArs);
            case "change-film-name" -> filmView.editFilmName(commandArs);
            case "add-film" -> filmView.addFilm(commandArs);
            case "add-actor-to-film" -> filmView.addActor(commandArs);
            case "exit" -> exitView.exit();
            default -> throw new CommandNotFoundException(commandName);
        }
    }
}
