import java.util.ArrayList;

class Operator {
    // <Оператор>::= <Присваивание>|<Сложный оператор>
    static ASTNode analyze() {
        ArrayList<ASTNode> children = new ArrayList<>();
        ASTNode complexOperatorNode;

        ASTNode assignmentNode = Assignment.analyze();

        if (assignmentNode != null)
            children.add(assignmentNode);
        else {
            complexOperatorNode = ComplexOperator.analyze();
            if (complexOperatorNode != null)
                children.add(complexOperatorNode);
        }

        return utils.constructTree(GrammarSymbols.OPERATOR, children);
    }
}