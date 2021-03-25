package unit6.task1;

import java.io.IOException;
import java.util.Map;

public class PropertyRepository {

    private final PropertyReader propertyReader = new PropertyReader();
    private Map<String, String> properties;
    private final String fileName;

    public PropertyRepository(String fileName) {
        this.fileName = fileName;
        properties = null;
    }

    public Map<String, String> getProperties() {
        if (properties == null) {
            try {
                properties = propertyReader.readPropertyFile(fileName);
            } catch (IOException e) {
                throw  new PropertiesInitializationException("проблемы с чтением файла.", e);
            }
        }
        return properties;
    }
}
