import java.util.ArrayList;

class Expression {

    // <Выражение> ::= <Ун.оп.> <Подвыражение> | <Подвыражение>
    static ASTNode analyze() {
        ArrayList<ASTNode> children = new ArrayList<>();

        ASTNode unaryNode = unaryOperator();
        if (unaryNode != null) children.add(unaryNode);

        ASTNode subExpressionNode = SubExpression.analyze();
        if (subExpressionNode == null) return null;

        children.add(subExpressionNode);

        return utils.constructTree(GrammarSymbols.EXPRESSION, children);
    }

    //  <Ун.оп.>
    private static ASTNode unaryOperator() {
        return OperatorSign.unaryOperator();
    }
}