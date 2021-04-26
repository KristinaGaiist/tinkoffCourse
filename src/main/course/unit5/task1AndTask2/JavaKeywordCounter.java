package unit5.task1AndTask2;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class JavaKeywordCounter {

    public static Map<JavaKeyword, Long> calculateJavaKeywordsInString(Map<JavaKeyword, Long> existingData,
                                                                       String stringToCalculate) {
        String fileWithoutSpecialSymbols = stringToCalculate
            .replaceAll("[^a-zA-Z0-9]", " ")
            .toUpperCase();
        HashMap<JavaKeyword, Long> result = new HashMap<>(existingData);
        for (JavaKeyword javaKeyword : JavaKeyword.values()) {
            long javaKeywordCount = Pattern.compile(javaKeyword.name())
                .splitAsStream(fileWithoutSpecialSymbols)
                .count() - 1;
            if (javaKeywordCount < 1) {
                continue;
            }
            if (result.containsKey(javaKeyword)) {
                javaKeywordCount += result.get(javaKeyword);
            }
            result.put(javaKeyword, javaKeywordCount);
        }
        return result;
    }
}
