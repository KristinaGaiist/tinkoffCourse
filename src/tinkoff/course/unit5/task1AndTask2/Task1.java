package unit5.task1AndTask2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Task1 {

    public static void main(String... args) {
        Map<JavaKeyword, Long> result = new HashMap<>();
        try {
            byte[] array = getByteArray();
            JavaKeywordCounter.calculateJavaKeywordsInString(result, new String(array, StandardCharsets.UTF_8));
            writeFile(result);
        } catch (PublicException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(ExceptionMessage.ERROR);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static byte[] getByteArray() throws PublicException {
        try (FileInputStream inputStream =
            new FileInputStream(new File("src\\tinkoff\\resources\\java-code"))) {
            return inputStream.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new PublicException(ExceptionMessage.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new PublicException(ExceptionMessage.ERROR_WITH_READ_FILE, e);
        }
    }

    private static void writeFile(Map<JavaKeyword, Long> result) throws PublicException {
        try (FileOutputStream outputStream =
            new FileOutputStream(new File("src\\tinkoff\\resources\\task1-java-keywords"))) {
            for (Entry<JavaKeyword, Long> entry : result.entrySet()) {
                String line = entry.getKey() + " " + entry.getValue() + "\n";
                outputStream.write(line.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            throw new PublicException(ExceptionMessage.ERROR_WITH_WRITE_FILE, e);
        }
    }
}
