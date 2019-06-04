class KeyWords {
    static ASTNode begin() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.BEGIN) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            SyntaxAnalyzer.removeLineBreak();
            return new ASTNode(GrammarSymbols.BEGIN, lexeme);
        }
        return null;
    }

    // Var
    static ASTNode variable() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.VAR) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            return new ASTNode(GrammarSymbols.VAR, lexeme);
        }
        return null;
    }

    // End
    static ASTNode end() {
        Lexeme lexeme = SyntaxAnalyzer.getCurrentLexeme();
        if (lexeme.getType() == LexemeType.END) {
            SyntaxAnalyzer.moveToTheNextLexeme();
            SyntaxAnalyzer.removeLineBreak();
            return new ASTNode(GrammarSymbols.END, lexeme);
        }
        return null;
    }

    static boolean isEnd() {
        LexemeType lexemeType = SyntaxAnalyzer.getCurrentLexeme().getType();
        return (lexemeType == LexemeType.END || lexemeType == LexemeType.UNTIL);
    }
}