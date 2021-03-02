package unit5.task1AndTask2;

import java.util.Map;
import java.util.regex.Pattern;

public class JavaKeywordCounter {

    public static void calculateJavaKeywordsInString(Map<JavaKeyword, Long> result, String stringToCalculate) {
        String fileWithoutSpecialSymbols = stringToCalculate
            .replaceAll("[^a-zA-Z0-9]", " ")
            .toUpperCase();

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
    }
}
