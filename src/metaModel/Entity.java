package metaModel;

import java.util.ArrayList;
import java.util.List;

public class Entity extends Identifier implements MinispecElement {
	private String name;

	List<Attribute> attributes;

	public Entity() {
		this.attributes = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void accept(Visitor v) {
		v.visitEntity(this);
	};

	public void addAttribute(Attribute e) {
		this.attributes.add(e);
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
}
