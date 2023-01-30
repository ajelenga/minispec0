package metaModel;

import java.util.HashMap;

public abstract class Type extends Identifier implements MinispecElement {
    protected String name;
    protected HashMap<String, Type> tabtype;

    public Type() {
        this.tabtype = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Type> getTabtype() {
        return tabtype;
    }

    public void addType(String s, Type e) {
        this.tabtype.put(s, e);
    }

    public void accept(Visitor v) {
        v.visitType(this);
    }

}