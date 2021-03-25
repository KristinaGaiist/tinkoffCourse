package unit5.task3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import unit5.task3.entity.Film;

public class JsonStorage {

    private final String filePath;
    private final Gson gson;

    public JsonStorage(String fileName) {

        this.filePath = fileName;
        this.gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    }

    public List<Film> read() throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Film>>() {}.getType());
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void save(List<Film> list) throws IOException {
        String json = gson.toJson(list);

        try (Writer writer = new FileWriter(filePath)) {
            writer.write(json, 0, json.length());
        }
    }
}
