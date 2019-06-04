import java.util.ArrayList;

class SyntaxAnalyzer {
    private static ArrayList<Lexeme> lexemeList = new ArrayList<>();
    private static Lexeme currentLexeme = new Lexeme(LexemeType.UNRECOGNISED, "");
    private static int currentLexemeIndex = 0;

    static void setLexemeList(ArrayList<Lexeme> lexemes) {
        if (lexemeList.isEmpty()) {
            lexemeList = lexemes;
            currentLexeme = lexemeList.get(0);
        }
    }

    static Lexeme getCurrentLexeme() {
        return currentLexeme;
    }

    static void moveToTheNextLexeme() {
        getNextLexeme();
    }

    private static void getNextLexeme() {
        if (currentLexemeIndex + 1 < lexemeList.size()) {
            currentLexeme = lexemeList.get(currentLexemeIndex + 1);
            currentLexemeIndex++;
        }
    }

    static void skipCurrentLine() {
        while (currentLexeme.getType() != LexemeType.LINEBREAK) getNextLexeme();
        getNextLexeme();
    }

    static Boolean removeLineBreak() {
        Lexeme lexeme = getCurrentLexeme();
        if (lexeme.getType() == LexemeType.LINEBREAK) {
            ErrorLog.nextLine();
            moveToTheNextLexeme();
            return true;
        }
        return false;
    }

    static ASTNode beginAnalise() {
        return program();
    }

    // <Программа> ::= <Объявление переменных> <Описание вычислений>
    private static ASTNode program() {
        ASTNode declareVariablesNode = declareVariables();
        ASTNode declareCalculationsNode = declareCalculations();

        if (currentLexemeIndex != lexemeList.size() - 1)
            ErrorLog.logError("Some extra lines after the end of the calculations block ");

        return utils.constructTree(GrammarSymbols.PROGRAM, new ArrayList<>() {{
            add(declareVariablesNode);
            add(declareCalculationsNode);
        }});
    }

    // <Объявление переменных> ::= Var <Список переменных>
    private static ASTNode declareVariables() {
        ASTNode variableNode = KeyWords.variable();
        if (variableNode == null) {
            ErrorLog.logError("No key word 'Var' at the start of variables declare");
            return null;
        }
        ASTNode variablesListNode = variablesList();
        if (variablesListNode == null) return null;


        if (!VariablesList.isVariableListEnded()) {
            ErrorLog.logError("No semicolon at the end of variables declare");
            return null;
        }
        moveToTheNextLexeme();
        removeLineBreak();

        return utils.constructTree(GrammarSymbols.DECLARE_VARIABLES, new ArrayList<>() {{
            add(variableNode);
            add(variablesListNode);
        }});
    }

    // <Описание вычислений> ::= Begin < Список операторов > End
    private static ASTNode declareCalculations() {
        var beginNode = KeyWords.begin();
        if (beginNode == null) {
            ErrorLog.logError("No begin at the start of calculation");
            return null;
        }

        ASTNode operatorsListNode = operatorsList();

        ASTNode endNode = KeyWords.end();
        if (endNode == null) {
            ErrorLog.logError("No end at the end of calculation");
            return null;
        }

        return utils.constructTree(
                GrammarSymbols.DECLARE_CALCULATIONS,
                new ArrayList<>() {{
                    add(beginNode);
                    add(operatorsListNode);
                    add(endNode);
                }}
        );
    }

    // <Список переменных> ::= <Идент> | <Идент> , <Список переменных>
    private static ASTNode variablesList() {
        return VariablesList.analyze();
    }

    //<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
    private static ASTNode operatorsList() {
        return OperatorsList.analyze();
    }
}
