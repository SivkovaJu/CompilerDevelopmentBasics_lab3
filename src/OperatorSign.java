class OperatorSign {

    // "-" | "+" | "*" | "/" | ">=" | "<=" | "="
    static ASTNode binaryOperator() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.BIN_MATH_OPERATOR || lexeme.getType() == LexemeType.RELATION_OPERATOR) {
            SyntaxAnalyzer.moveToTheNextLexeme();

            GrammarSymbols operatorType = findOperatorType(lexeme.getSign());
            return new ASTNode(operatorType, lexeme);
        }
        return null;
    }

    private static GrammarSymbols findOperatorType(String value) {
        for (GrammarSymbols symbol : GrammarSymbols.values()) {
            if (symbol.getSign().equals(value)) return symbol;
        }
        return null;
    }

    // "-" | "not"
    static ASTNode unaryOperator() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.UNI_MATH_OPERATOR) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return new ASTNode(GrammarSymbols.UNARY_OPERATOR, lexeme);
        }
        return null;
    }

    // "("
    static Boolean leftBrace() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.LBRACE) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return true;
        }
        return false;
    }

    // ")"
    static boolean rightBrace() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.RBRACE) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return true;
        }
        return false;
    }

    // "REPEAT"
    static ASTNode repeatSign() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();

        if (lexeme.getType() == LexemeType.REPEAT) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return new ASTNode(GrammarSymbols.REPEAT, lexeme);
        }
        return null;
    }

    // "UNTIL"
    static ASTNode untilSign() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.UNTIL) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return new ASTNode(GrammarSymbols.UNTIL, lexeme);
        }
        return null;
    }

    // "," | ";"
    static boolean comma() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.COMMA || lexeme.getType() == LexemeType.SEMICOLON) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return true;
        }
        return false;
    }

    static ASTNode assignmentSign() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.DECLARE) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return new ASTNode(GrammarSymbols.ASSIGNMENT_SIGN, lexeme);
        }
        return null;
    }
}