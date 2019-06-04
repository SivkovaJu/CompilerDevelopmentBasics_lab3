import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Lexeme> lexemeList = LexemeStreamReader.parseLexemeParserOutput();
        if (lexemeList == null) return;

        System.out.println("Base program");
        for (Lexeme lexeme : lexemeList) {
            if (lexeme.getType() == LexemeType.LINEBREAK)
                System.out.println();
            else
                System.out.print(lexeme.getSign() + " ");
        }

        System.out.println("\n\nTree");

        SyntaxAnalyzer.setLexemeList(lexemeList);
        ASTNode ast = SyntaxAnalyzer.beginAnalise();
        ErrorLog.showErrorList();
        utils.printAST(ast, 0);

    }
}
