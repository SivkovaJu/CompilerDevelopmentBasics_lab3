class Operand {

    // <Операнд> ::= <Идент> | <Const>
    static ASTNode analyze() {
        ASTNode node = identifier();
        if (node == null) return constant();
        else return node;
    }

    // <Идент>
    static ASTNode identifier() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.IDENTIFIER) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return new ASTNode(GrammarSymbols.IDENTIFIER, lexeme);
        }
        return null;
    }

    // <Const>
    static private ASTNode constant() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.CONST) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return new ASTNode(GrammarSymbols.CONST, lexeme);
        }
        return null;
    }
}