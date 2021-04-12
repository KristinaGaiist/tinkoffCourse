package unit9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Task3 implements Runnable {

    private final File file;
    private final List<String> lines;

    public Task3(File file, List<String> lines) {
        this.file = file;
        this.lines = lines;
    }

    public static void main(String[] args) {
        List<String> lines = Collections.synchronizedList(new ArrayList<>());
        CompletableFuture.allOf(
            CompletableFuture.runAsync(new Task3(new File("src\\tinkoff\\resources\\unit8-task3-copy1"), lines)),
            CompletableFuture.runAsync(new Task3(new File("src\\tinkoff\\resources\\unit8-task3-copy2"), lines))
        ).join();

        lines.forEach(System.out::println);
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Файл не найден: " + file.getAbsolutePath());
            exception.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
            e.printStackTrace();
        }
    }
}
