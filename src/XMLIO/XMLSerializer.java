package XMLIO;

import metaModel.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XMLSerializer extends Visitor {
    List<Element> elements;
    Element root;
    String modelId;
    String entityID;
    String attributeID;
    String typeID;
    Document doc;

    Document result() {
        return this.doc;
    }

    public XMLSerializer() throws ParserConfigurationException {
        this.elements = new ArrayList<>();
        //this.counter = 0;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.doc = builder.newDocument();
        root = this.doc.createElement("Root");
        this.doc.appendChild(root);
    }

    private void addIdToElement(Element e, String id) {
        Attr attr = this.doc.createAttribute("id");
        attr.setValue(id);
        e.setAttributeNode(attr);
    }

    private void maybeUpdateRootFrom(Element e) {
        String rootId = this.root.getAttribute("model");
        if (rootId.isEmpty()) {
            Attr attr = this.doc.createAttribute("model");
            attr.setValue(modelId);
            this.root.setAttributeNode(attr);
        }
    }

    @Override
    public void visitAttribute(Attribute e) {
        super.visitAttribute(e);
        attributeID = e.getIDentifiant();

        Element elem = this.doc.createElement("Attribute");
        this.addIdToElement(elem, attributeID);
        Attr attr = doc.createAttribute("entity");
        attr.setValue(entityID);
        elem.setAttributeNode(attr);

        attr = doc.createAttribute("name");
        attr.setValue(e.getName());
        elem.setAttributeNode(attr);

        attr = doc.createAttribute("type");
        attr.setValue(e.getType().getIDentifiant());
        elem.setAttributeNode(attr);

        this.root.appendChild(elem);
        elements.add(elem);
        for (Map.Entry mapentry : e.getType().getTabtype().entrySet()) {
            if (mapentry.getKey().equals(attributeID))
                ((Type) mapentry.getValue()).accept(this);
        }
    }

    public void visitType(Type e) {
        super.visitType(e);
        typeID = e.getIDentifiant();

        if (e instanceof TypeSimple) {
            Element elem = this.doc.createElement("TypeSimple");
            this.addIdToElement(elem, typeID);
            Attr attr = doc.createAttribute("name");
            attr.setValue(e.getName());
            elem.setAttributeNode(attr);
            this.root.appendChild(elem);
            elements.add(elem);
        }
    }

    @Override
    public void visitEntity(Entity e) {
        super.visitEntity(e);
        entityID = e.getIDentifiant();

        Element elem = this.doc.createElement("Entity");
        this.addIdToElement(elem, entityID);
        Attr attr = doc.createAttribute("model");
        attr.setValue(modelId);
        elem.setAttributeNode(attr);

        attr = doc.createAttribute("name");
        attr.setValue(e.getName());
        elem.setAttributeNode(attr);

        this.root.appendChild(elem);
        elements.add(elem);
        for (Attribute n : e.getAttributes()) {
            n.accept(this);
        }
    }

    @Override
    public void visitModel(Model e) {
        super.visitModel(e);
        modelId = e.getIDentifiant();
        Element elem = this.doc.createElement("Model");
        this.addIdToElement(elem, modelId);
        this.maybeUpdateRootFrom(elem);
        this.root.appendChild(elem);
        elements.add(elem);
        for (Entity n : e.getEntities()) {
            n.accept(this);
        }
    }


}
