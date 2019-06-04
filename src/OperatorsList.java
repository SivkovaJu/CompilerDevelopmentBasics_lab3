import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

//<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
class OperatorsList {

    // <Список операторов> ::= <Оператор><Продолжение списка операторов>
    static ASTNode analyze() {
        ArrayList<ASTNode> children = operatorsList();
        return utils.constructTree(GrammarSymbols.OPERATORS_LIST, children);
    }

    // <Оператор><Продолжение списка операторов>
    private static ArrayList<ASTNode> operatorsList() {
        ArrayList<ASTNode> children = new ArrayList<>();

        ASTNode operatorNode = operator();

        if (operatorNode != null)
            children.add(operatorNode);

        List<ASTNode> continueOfOperatorListNode = continueOfOperatorList();
        children.addAll(continueOfOperatorListNode);
        return children;
    }

    // <Продолжение списка операторов> ::= Ɛ | <Список операторов>
    private static List<ASTNode> continueOfOperatorList() {
        if (KeyWords.isEnd())
            return emptyList();
        return operatorsList();
    }

    // <Оператор>
    private static ASTNode operator() {
        return Operator.analyze();
    }
}