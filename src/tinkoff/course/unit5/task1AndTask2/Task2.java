package unit5.task1AndTask2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Task2 {

    public static void main(String... args) {
        Map<JavaKeyword, Long> result = new HashMap<>();
        try {
            readFileAndCalculateResult(result);
            writeFile(result);
        } catch (PublicException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(ExceptionMessage.ERROR);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void readFileAndCalculateResult(Map<JavaKeyword, Long> result) throws PublicException {
        try (FileReader reader = new FileReader(new File("src\\tinkoff\\resources\\java-code"))) {
            int content;
            StringBuilder line = new StringBuilder();
            while ((content = reader.read()) != -1) {
                if (content == '\r' || content == '\n') {
                    JavaKeywordCounter.calculateJavaKeywordsInString(result, line.toString());
                    line = new StringBuilder();
                } else {
                    line.append((char) content);
                }
            }
        } catch (FileNotFoundException e) {
            throw new PublicException(ExceptionMessage.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new PublicException(ExceptionMessage.ERROR_WITH_READ_FILE, e);
        }
    }

    private static void writeFile(Map<JavaKeyword, Long> result) throws PublicException {
        try (FileWriter writer = new FileWriter(new File("src\\tinkoff\\resources\\task2-java-keywords"))) {
            for (Entry<JavaKeyword, Long> entry : result.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            throw new PublicException(ExceptionMessage.ERROR_WITH_WRITE_FILE, e);
        }
    }
}
