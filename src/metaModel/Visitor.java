package metaModel;


public abstract class Visitor {
	public void visitModel(Model e) {}
    public void visitEntity(Entity e) {}
    public void visitAttribute(Attribute attribute) {
    }
}
