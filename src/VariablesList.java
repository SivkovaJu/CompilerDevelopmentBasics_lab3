import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

// <Список переменных> ::= <Идент> | <Идент> , <Список переменных> ;
class VariablesList {

    private static boolean endOfVariableList = false;

    // <Список переменных> ::= <Идент> <Продолжение списка>
    static ASTNode analyze() {
        ASTNode identifierNode = identifier();

        if (identifierNode == null) {
            ErrorLog.logError("Bad variables declaration");
            SyntaxAnalyzer.skipCurrentLine();
            return null;
        }
        List<ASTNode> continueOfVariablesListNode = continueOfVariablesList();

        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(identifierNode);
        children.addAll(continueOfVariablesListNode);
        return utils.constructTree(GrammarSymbols.OPERATORS_LIST, children);
    }

    //<Продолжение списка переменных> ::= Ɛ | , <Список переменных>
    private static List<ASTNode> continueOfVariablesList() {
        if (SyntaxAnalyzer.removeLineBreak())
            return emptyList();

        return commaWithVariables();
    }

    // <Оператор>
    static private ASTNode identifier() {
        return Operand.identifier();
    }

    // , <Список переменных>
    static private List<ASTNode> commaWithVariables() {
        if (SyntaxAnalyzer.getCurrentLexeme().getType() == LexemeType.SEMICOLON) {
            endOfVariableList = true;
            return emptyList();
        }

        if (!OperatorSign.comma()) {
            ErrorLog.logError("Comma not found");
            SyntaxAnalyzer.skipCurrentLine();
            return emptyList();
        }

        ASTNode variablesListNode = analyze();
        if (variablesListNode == null) {
            ErrorLog.logError("No variables after comma");
            SyntaxAnalyzer.skipCurrentLine();
            return emptyList();
        }

        return variablesListNode.getChildren();
    }

    public static boolean isVariableListEnded() {
        return endOfVariableList;
    }
}