package shakespearesearch.controller.distributedsearch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtils {
    private static final String REGEX_SPECIAL_CHARS = "[\\[\\]\\{\\}\\(\\)\\-\\+\\*\\?\\^\\$\\\\\\.\\|]";

    public static Pattern buildPattern(String searchTerm) {
        String escapedTerm = searchTerm.replaceAll(REGEX_SPECIAL_CHARS, "\\\\$0");
        return Pattern.compile("\\b" + escapedTerm + "\\b", Pattern.CASE_INSENSITIVE);
    }

    public static Match createMatch(String line, int lineNumber, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return new Match(lineNumber, matcher.group());
        }
        return null;
    }
}