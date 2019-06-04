import java.util.ArrayList;

class Assignment {

    // <Присваивание> ::= <Идент> := <Выражение> ;
    static ASTNode analyze() {
        ASTNode identifierNode = identifier();
        if (identifierNode == null) return null;

        ASTNode assignmentSignNode = assignment();
        if (assignmentSignNode == null) {
            ErrorLog.logError("Нет знака присваивания");
            SyntaxAnalyzer.skipCurrentLine();
            return null;
        }

        ASTNode expressionNode = expression();
        if (expressionNode == null) {
            ErrorLog.logError("Нет правой части операции присваивания");
            SyntaxAnalyzer.skipCurrentLine();
            return null;
        }

        if (SyntaxAnalyzer.getCurrentLexeme().getType() != LexemeType.SEMICOLON) {
            ErrorLog.logError("Не хватает точки с запитой в конце выражения");
        }else SyntaxAnalyzer.moveToTheNextLexeme();

        SyntaxAnalyzer.removeLineBreak();
        return utils.constructTree(GrammarSymbols.ASSIGNMENT, new ArrayList<>() {{
            add(identifierNode);
            add(assignmentSignNode);
            add(expressionNode);
        }});
    }

    // <Идент>
    private static ASTNode identifier() {
        return Operand.identifier();
    }

    // :=
    private static ASTNode assignment() {
        return OperatorSign.assignmentSign();
    }

    // <Выражение>
    private static ASTNode expression() {
        return Expression.analyze();
    }
}