package unit6.task1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertyReader {

    public Map<String, String> readPropertyFile(String propertyFileName) throws IOException {
        Properties properties = new Properties();
        String propertyFilePath = String.format("src\\tinkoff\\resources\\%s.properties", propertyFileName);
        try(FileInputStream inputStream = new FileInputStream(propertyFilePath)) {
            properties.load(inputStream);
        }
        return new HashMap<>(properties.entrySet().stream()
                                 .collect(Collectors.toMap(e -> e.getKey().toString(),
                                                           e -> e.getValue().toString())));
    }
}
