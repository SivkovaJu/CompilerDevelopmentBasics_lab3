import java.util.ArrayList;

public class ASTNode {
    private Lexeme lexeme;
    private ASTNode parent;
    private GrammarSymbols type;
    private ArrayList<ASTNode> children = new ArrayList<>();

    ASTNode(GrammarSymbols type, Lexeme lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    void addChild(ASTNode child) {
        ASTNode prevParent = child.parent;
        if (prevParent != null) prevParent.getChildren().remove(child);
        this.children.add(child);
        child.setParent(this);
    }

    Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    public ASTNode getParent() {
        return parent;
    }

    void setParent(ASTNode parent) {
        this.parent = parent;
    }

    GrammarSymbols getType() {
        return type;
    }

    public void setType(GrammarSymbols type) {
        this.type = type;
    }

    ArrayList<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ASTNode> children) {
        this.children = children;
    }
}
