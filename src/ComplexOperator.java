import java.util.ArrayList;

class ComplexOperator {

    // <Сложный оператор> ::= REPEAT <Список операторов> UNTIL <Выражение>
    static ASTNode analyze() {
        ArrayList<ASTNode> children = new ArrayList<>();

        ArrayList<ASTNode> repeatNode = repeatUntil();
        if (!repeatNode.isEmpty())
            children.addAll(repeatNode);

        return utils.constructTree(GrammarSymbols.COMPLEX_OPERATOR, children);
    }

    // IF < Выражение> THEN <Оператор> <Продолжение IF THEN>
    static private ArrayList<ASTNode> repeatUntil() {
        ASTNode repeatSignNode = OperatorSign.repeatSign();
        if (repeatSignNode == null) return new ArrayList<>();

        if(SyntaxAnalyzer.getCurrentLexeme().getType() == LexemeType.LINEBREAK) SyntaxAnalyzer.moveToTheNextLexeme();

        ASTNode operatorNode = operator();
        if (operatorNode == null) {
            ErrorLog.logError("Нет выражения после repeat");
            SyntaxAnalyzer.skipCurrentLine();
            return new ArrayList<>();
        }

        ASTNode untilSignNode = OperatorSign.untilSign();
        if (untilSignNode == null) {
            ErrorLog.logError("Нет until после repeat");
            SyntaxAnalyzer.skipCurrentLine();
            return new ArrayList<>();
        }

        ASTNode expressionNode = Expression.analyze();
        if (expressionNode == null) {
            ErrorLog.logError("Нет условия после until");
            SyntaxAnalyzer.skipCurrentLine();
            return new ArrayList<>();
        }

        SyntaxAnalyzer.removeLineBreak();

        return new ArrayList<>() {{
            add(repeatSignNode);
            add(operatorNode);
            add(untilSignNode);
            add(expressionNode);
        }};
    }

    // <Оператор>
    static private ASTNode operator() {
        return OperatorsList.analyze();
    }
}