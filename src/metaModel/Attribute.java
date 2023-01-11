package metaModel;

public class Attribute extends Identifier implements MinispecElement{
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void accept(Visitor v) {
        v.visitAttribute(this);
    }
}
