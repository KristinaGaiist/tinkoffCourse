package unit5.task3.cmd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import unit2.task1.cmd.Messages;
import unit5.task3.application.CommitService;
import unit5.task3.application.FilmService;
import unit5.task3.cmd.view.ExitView;
import unit5.task3.cmd.view.FilmView;
import unit5.task3.persistence.FilmRepository;

public class Main {

    public static void main(String... args) {

        FilmRepository storage = new FilmRepository();
        FilmView filmView = new FilmView(new FilmService(storage));
        ExitView exitView = new ExitView(new CommitService(storage));
        ShellCommandExecutor commandExecutor = new ShellCommandExecutor(filmView, exitView);

        try (InputStream inputStream = System.in;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (true) {
                System.out.println(Messages.INSERT_COMMAND);
                String command = bufferedReader.readLine();
                String[] arguments = Arrays.stream(command.split("\""))
                    .map(String::trim)
                    .toArray(String[]::new);

                commandExecutor.execute(arguments);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Произошла непредвиденная ошибка");
        }
    }
}
