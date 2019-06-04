import java.util.ArrayList;
import java.util.List;

class SubExpression {

    // <Подвыражение> :: = ( <Выражение> ) <Вспомогательное выражение>  | <Операнд> <Вспомогательное выражение>
    static ASTNode analyze() {
        ArrayList<ASTNode> children = new ArrayList<>();

        List<ASTNode> bracedExpressionNode = startsWithBracedExpression();
        if(bracedExpressionNode.isEmpty()){
            ArrayList<ASTNode> subExpressionWithOperandNode = subExpressionWithOperand();
            children.addAll(subExpressionWithOperandNode);
        }
        else children.addAll(bracedExpressionNode);

        if(children.size() == 1)
            return children.get(0);

        return utils.constructTree(GrammarSymbols.SUB_EXPRESSION, children);
    }

    // ( <Выражение> ) <Вспомогательное выражение>
    private static List<ASTNode> startsWithBracedExpression() {
        ArrayList<ASTNode> children = new ArrayList<>();

        ASTNode bracedExpressionNode = bracedExpression();
        if (bracedExpressionNode == null) return new ArrayList<>();
        children.add(bracedExpressionNode);

        ArrayList<ASTNode> helpfulExpressionNode = helpfulExpression();
        children.addAll(helpfulExpressionNode);

        return children;
    }

    // ( <Выражение> )
    private static ASTNode bracedExpression() {
        if (!OperatorSign.leftBrace()) return null;
        ASTNode expressionNode = Expression.analyze();
        if (expressionNode == null) return null;
        if (!OperatorSign.rightBrace()) return null;
        return expressionNode;
    }

    // <Операнд> <Вспомогательное выражение>
    private static ArrayList<ASTNode> subExpressionWithOperand() {
        ArrayList<ASTNode> children = new ArrayList<>();

        ASTNode operandNode = operand();
        if (operandNode == null) return new ArrayList<>();
        children.add(operandNode);

        ArrayList<ASTNode> helpfulExpressionNode = helpfulExpression();
        children.addAll(helpfulExpressionNode);

        return children;
    }

    // <Вспомогательное выражение> :: = <Бин.оп.><Подвыражение><Вспомогательное выражение> | Ɛ
    private static ArrayList<ASTNode> helpfulExpression() {
        if (SyntaxAnalyzer.removeLineBreak())
            return new ArrayList<>();

        return subExpressionsWithBinaryOperator();
    }

    // <Бин.оп.><Подвыражение><Вспомогательное выражение>
    private static ArrayList<ASTNode> subExpressionsWithBinaryOperator() {
        ArrayList<ASTNode> children = new ArrayList<>();

        ASTNode binaryOperator = binaryOperator();
        if (binaryOperator == null) return new ArrayList<>();
        children.add(binaryOperator);

        ASTNode subExpressionNode = analyze();
        if (subExpressionNode == null) return new ArrayList<>();
        children.add(subExpressionNode);

        ArrayList<ASTNode> helpfulExpressionNode = helpfulExpression();
        children.addAll(helpfulExpressionNode);

        return children;
    }

    // <Операнд>
    private static ASTNode operand() {
        return Operand.analyze();
    }

    // <Бин.оп.>
    private static ASTNode binaryOperator() {
        return OperatorSign.binaryOperator();
    }
}