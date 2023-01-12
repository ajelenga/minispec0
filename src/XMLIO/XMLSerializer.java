package XMLIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import metaModel.Attribute;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import metaModel.Entity;
import metaModel.Model;
import metaModel.Visitor;

public class XMLSerializer extends Visitor {
    List<Element> elements;
    Element root = null;
    String modelId;
    String entityID;
    String attributeID;
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
    private void addIdToElement(Element e,String id) {
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
        attributeID=e.getIDentifiant();

        Element elem = this.doc.createElement("Attribute");
        this.addIdToElement(elem,entityID);
        Attr attr = doc.createAttribute("entity");
        attr.setValue(entityID);
        elem.setAttributeNode(attr);

        attr = doc.createAttribute("name");
        attr.setValue(e.getName());
        elem.setAttributeNode(attr);

        attr = doc.createAttribute("type");
        attr.setValue(e.getType());
        elem.setAttributeNode(attr);

        this.root.appendChild(elem);
        elements.add(elem);
    }
    @Override
    public void visitEntity(Entity e) {
        super.visitEntity(e);
        entityID=e.getIDentifiant();

        Element elem = this.doc.createElement("Entity");
        this.addIdToElement(elem,entityID);
        Attr attr = doc.createAttribute("model");
        attr.setValue(modelId);
        elem.setAttributeNode(attr);

        attr = doc.createAttribute("name");
        attr.setValue(e.getName());
        elem.setAttributeNode(attr);

        this.root.appendChild(elem);
        elements.add(elem);
        for (Attribute n: e.getAttributes()) {
           n.accept(this);
        }
    }

    @Override
    public void visitModel(Model e) {
        super.visitModel(e);
        modelId = e.getIDentifiant();
        Element elem = this.doc.createElement("Model");
        this.addIdToElement(elem,modelId);
        this.maybeUpdateRootFrom(elem);
        this.root.appendChild(elem);
        elements.add(elem);
        for (Entity n : e.getEntities()) {
            n.accept(this);
        }
    }



}
