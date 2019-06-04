import java.util.ArrayList;

enum GrammarSymbols {
    PROGRAM("<Программа>"),
    DECLARE_VARIABLES("<Объявление переменных>"),
    DECLARE_CALCULATIONS("<Описание вычислений>"),
    OPERATORS_LIST("<Список операторов>"),
    VARIABLES_LIST("<Список переменных>"),
    IDENTIFIER("<Идент>"),
    OPERATOR("<Оператор>"),
    ASSIGNMENT("<Присваивание>"),
    COMPLEX_OPERATOR("<Сложный оператор>"),
    EXPRESSION("<Выражение>"),
    SUB_EXPRESSION("<Подвыражение>"),
    UNARY_OPERATOR("<Ун.оп.>"),
    OPERAND("<Операнд>"),
    BINARY_OPERATOR("<Бин.оп.>"),
    CONST("<Const>"),
    COMPOUND_OPERATOR("<Составной оператор>"),
    BEGIN("Begin"),
    END("End"),
    VAR("Var"),
    COMMA(","),
    ASSIGNMENT_SIGN(":="),
    LEFT_BRACE("("),
    RIGHT_BRACE(")"),
    MINUS("-"),
    PLUS("+"),
    MUL("*"),
    DIV("/"),
    LEFT_SHIFT_SIGN(">="),
    RIGHT_SHIFT_SIGN("=<"),
    LESS_SIGN(">"),
    MORE_SIGN("<"),
    EQUAL_SIGN("="),
    SEMICOLON(";"),
    REPEAT("REPEAT"),
    UNTIL("UNTIL"),
    DIGIT("<Цифра>");

    private final String sign;

    public final String getSign() {
        return this.sign;
    }


    GrammarSymbols(String sign) {
        this.sign = sign;
    }
}

enum LexemeType {
    RELATION_OPERATOR(0),
    BIN_MATH_OPERATOR(1),
    UNI_MATH_OPERATOR(2),
    DECLARE(3),
    IDENTIFIER(4),
    CONST(5),
    BEGIN(6),
    END(7),
    VAR(8),
    REPEAT(9),
    UNTIL(10),
    SEMICOLON(11),
    LBRACE(12),
    RBRACE(13),
    COMMA(14),
    LINEBREAK(15),
    UNRECOGNISED(16);

    private final int code;

    public final int getCode() {
        return this.code;
    }

    LexemeType(int code) {
        this.code = code;
    }
}

class Lexeme {
    private LexemeType type;
    private String sign;

    Lexeme(LexemeType type, String sign) {
        this.type = type;
        this.sign = sign;
    }

    LexemeType getType() {
        return type;
    }

    String getSign() {
        return sign;
    }
}

class utils {
    static ASTNode constructTree(GrammarSymbols parentType, ArrayList<ASTNode> children) {
        if (children.isEmpty()) return null;

        ASTNode parent = new ASTNode(parentType, null);

        for (ASTNode child : children) {
            if (child == null) return parent;
            parent.addChild(child);
            child.setParent(parent);
        }
        return parent;
    }

    static void printAST(ASTNode Node, int level) {
        if (Node == null) System.out.println("Cannot produce the tree");
        else {
            var tabulate = level;
            while (tabulate > 0) {
                System.out.print("\t");
                tabulate--;
            }
            if (Node.getLexeme() != null) System.out.print("|-- " + Node.getLexeme().getSign());
            else System.out.print("|-- " + Node.getType().getSign());

            if (!Node.getChildren().isEmpty()) System.out.println(" -->");
            else System.out.println();

            for (ASTNode child : Node.getChildren()) printAST(child, level + 1);
        }
    }
}

class ErrorLog {
    private static int lineNum = 1;
    private static ArrayList<String> errorTray = new ArrayList<>();

    static void logError(String msg) {
        errorTray.add("Error in line " + lineNum + ": " + msg);
    }

    static void showErrorList() {
        errorTray.forEach(System.out::println);
    }

    static void nextLine() {
        lineNum++;
    }
}