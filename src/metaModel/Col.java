package metaModel;

public class Col extends Type {

    protected Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Col(String name) {
        super();
        this.name = name;
    }

    public Col() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
