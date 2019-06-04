import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LexemeStreamReader {

    private static final String path = "D:/lab3.txt";

    static private List<String> readFile() {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    static private Lexeme getLexemeByString(String lexeme) {
        Pattern regex = Pattern.compile("<(\\d+),(\\S+)>");
        Matcher matcher = regex.matcher(lexeme);
        matcher.find();
        String type = matcher.group(1);
        String sign = matcher.group(2);
        LexemeType lexemeType = LexemeType.UNRECOGNISED;
        for (LexemeType el : LexemeType.values())
            if (el.getCode() == Integer.parseInt(type)) {
                lexemeType = el;
                break;
            }

        return new Lexeme(lexemeType, sign);
    }

    static ArrayList<Lexeme> parseLexemeParserOutput() {
        ArrayList<Lexeme> lexemeList = new ArrayList<>();
        List<String> lexemeStringList = readFile();
        if (lexemeStringList == null) return null;

        for (String lexeme : lexemeStringList)
            lexemeList.add(getLexemeByString(lexeme));
        return lexemeList;
    }
}