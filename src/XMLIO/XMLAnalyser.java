package XMLIO;

import metaModel.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLAnalyser {

    // Les clés des 2 Map sont les id

    // Map des instances de la syntaxe abstraite (metamodel)
    protected Map<String, MinispecElement> minispecIndex;
    // Map des elements XML
    protected Map<String, Element> xmlElementIndex;

    public XMLAnalyser() {
        this.minispecIndex = new HashMap<String, MinispecElement>();
        this.xmlElementIndex = new HashMap<String, Element>();
    }

    protected Model modelFromElement(Element e) {
        String identifier = e.getAttribute("id");
        Model model = new Model();
        model.setIDentifiant(identifier);
        return model;
    }

    protected Entity entityFromElement(Element e) {
        String name = e.getAttribute("name");
        String identifier = e.getAttribute("id");
        Entity entity = new Entity();
        entity.setIDentifiant(identifier);
        entity.setName(name);
        Model model = (Model) minispecElementFromXmlElement(this.xmlElementIndex.get(e.getAttribute("model")));
        model.addEntity(entity);
        return entity;
    }

    protected Attribute attributeFromElement(Element e) {
        String name = e.getAttribute("name");

        String identifier = e.getAttribute("id");
        Attribute attribute = new Attribute();
        attribute.setIDentifiant(identifier);
        attribute.setName(name);

        Type type = (Type) minispecElementFromXmlElement(this.xmlElementIndex.get(e.getAttribute("type")));
        type.addType(identifier, type);

        attribute.setType(type);
        Entity entity = (Entity) minispecElementFromXmlElement(this.xmlElementIndex.get(e.getAttribute("entity")));
        entity.addAttribute(attribute);

        return attribute;
    }

    protected TypeSimple typeSimpleFromElement(Element e) {
        String id = e.getAttribute("id");
        String name = e.getAttribute("name");
        TypeSimple type = new TypeSimple();
        type.setName(name);
        type.setIDentifiant(id);
        return type;
    }

    protected Col ListFromElement(Element e) {
        String identifier = e.getAttribute("id");
        Type type = (Type) minispecElementFromXmlElement(this.xmlElementIndex.get(e.getAttribute("type")));
        type.addType(identifier, type);
        Col col = new Col();
        String name;
        String choice = e.getAttribute("typeCol");
        switch (choice) {

            case "List":
                name = "ArrayList<" + type.getName() + "> ";
                name = name + e.getAttribute("name");
                col.setIDentifiant(identifier);
                col.setName(name);
                col.setType(type);
                break;

            case "Array":
                name = "Array<" + type.getName() + "> ";
                name = name + e.getAttribute("name");
                col.setIDentifiant(identifier);
                col.setName(name);
                col.setType(type);
                break;

            case "Set":
                name = "HashSet<" + type.getName() + "> ";
                name = name + e.getAttribute("name");
                col.setIDentifiant(identifier);
                col.setName(name);
                col.setType(type);
                break;
            case "Bag":
                name = "HashSet<" + type.getName() + "> ";
                name = name + e.getAttribute("name");
                col.setIDentifiant(identifier);
                col.setName(name);
                col.setType(type);
                break;
        }
        return col;

    }

    protected MinispecElement minispecElementFromXmlElement(Element e) {
        String id = e.getAttribute("id");
        MinispecElement result = this.minispecIndex.get(id);
        if (result != null) return result;
        String tag = e.getTagName();
        switch (tag) {
            case "Model":
                result = modelFromElement(e);
                break;
            case "Entity":
                result = entityFromElement(e);
                break;
            case "Attribute":
                result = attributeFromElement(e);
                break;
            case "Collection":
                result = ListFromElement(e);
                break;
            case "TypeSimple":
                result = typeSimpleFromElement(e);
                break;
        }
        this.minispecIndex.put(id, result);
        return result;
    }

    // alimentation du map des elements XML
    protected void firstRound(Element el) {
        NodeList nodes = el.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n instanceof Element) {
                Element child = (Element) n;
                String id = child.getAttribute("id");
                this.xmlElementIndex.put(id, child);
            }
        }
    }

    // alimentation du map des instances de la syntaxe abstraite (metamodel)
    protected void secondRound(Element el) {
        NodeList nodes = el.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n instanceof Element) {
                minispecElementFromXmlElement((Element) n);
            }
        }
    }

    public Model getModelFromDocument(Document document) {
        Element e = document.getDocumentElement();

        firstRound(e);

        secondRound(e);

        Model model = (Model) this.minispecIndex.get(e.getAttribute("model"));

        return model;
    }

    public Model getModelFromInputStream(InputStream stream) {
        try {
            // création d'une fabrique de documents
            DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();

            // création d'un constructeur de documents
            DocumentBuilder constructeur = fabrique.newDocumentBuilder();
            Document document = constructeur.parse(stream);
            return getModelFromDocument(document);

        } catch (ParserConfigurationException pce) {
            System.out.println("Erreur de configuration du parseur DOM");
            System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
        } catch (SAXException se) {
            System.out.println("Erreur lors du parsing du document");
            System.out.println("lors de l'appel à construteur.parse(xml)");
        } catch (IOException ioe) {
            System.out.println("Erreur d'entrée/sortie");
            System.out.println("lors de l'appel à construteur.parse(xml)");
        }
        return null;
    }

    public Model getModelFromString(String contents) {
        InputStream stream = new ByteArrayInputStream(contents.getBytes());
        return getModelFromInputStream(stream);
    }

    public Model getModelFromFile(File file) {
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return getModelFromInputStream(stream);
    }

    public Model getModelFromFilenamed(String filename) {
        File file = new File(filename);
        return getModelFromFile(file);
    }
}
