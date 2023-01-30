package GenCode;

import metaModel.Attribute;
import metaModel.Entity;
import metaModel.Model;
import metaModel.Visitor;

public class GenCode extends Visitor {
    String result = "";
    String propDecl = "";
    String constr = "";
    String getter = "";
    String setter = "";

    public String result() {
        return result;
    }

    public void visitModel(Model e) {
        result = "model ;\n\n";
        for (Entity n : e.getEntities()) {
            n.accept(this);
        }
        result = result + "\nend model\n";

    }

    public void visitEntity(Entity e) {
        if (e.getAttributes() == null) {
            return;
        }
        result = result + "public class " + e.getName() + " {\n";

        for (Attribute a : e.getAttributes()) {
            this.propDecl = this.propDecl + a.getType().getName() + " " + a.getName() + "; \n";
            this.getter = this.getter + "public " + a.getType().getName() + " " + "get" + a.getName() + "()" + "{ return " + a.getName() + " ; } \n";
            this.setter = this.setter + "public void " + "set" + a.getName() + "(" + a.getType().getName() + " " + a.getName() + ")" + "{ this." + a.getName() + "=" + a.getName() + "; }\n";
        }
        constr = constr + "public " + e.getName() + "() {}";
        result = result + this.propDecl + this.getter + this.setter + "}\n";
        this.propDecl = "";
        this.getter = "";
        this.setter = "";

    }

}
