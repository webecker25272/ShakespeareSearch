package shakespearesearch.utils.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shakespearesearch.utils.threading.Match;

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