package XMLIO;


import metaModel.Attribute;
import metaModel.Model;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

class XMLAnalyserTest {

    @Test
    void test1() {
        XMLAnalyser analyser = new XMLAnalyser();
        Model model = analyser.getModelFromFilenamed("Exemple1.xml");
        assertTrue(model != null);
        assertTrue(model.getEntities().size() == 0);
    }

    @Test
    void test2() {
        XMLAnalyser analyser = new XMLAnalyser();
        Model model = analyser.getModelFromFilenamed("Exemple2.xml");
        assertTrue(model != null);
        System.out.println("ID model : " + model.getIDentifiant());
        assertTrue(model.getIDentifiant().equals("#1"));
        System.out.println("Nombre d'entity " + model.getEntities().size());
        assertTrue(model.getEntities().size() == 3);
        System.out.println("\n");
        System.out.println("ID entity: " + model.getEntities().get(0).getIDentifiant());
        assertTrue(model.getEntities().get(0).getIDentifiant().equals("#20"));
        System.out.println("Nombre d'attribut " + model.getEntities().get(0).getAttributes().size());
        assertTrue(model.getEntities().get(0).getAttributes().size() == 2);
        for (Attribute attr : model.getEntities().get(0).getAttributes()) {
            System.out.println("ID attribute:" + attr.getIDentifiant());
        }
        System.out.println("\n");
        System.out.println("ID entity: " + model.getEntities().get(1).getIDentifiant());
        assertTrue(model.getEntities().get(1).getIDentifiant().equals("#30"));
        System.out.println("Nombre d'attribut " + model.getEntities().get(1).getAttributes().size());
        assertTrue(model.getEntities().get(1).getAttributes().size() == 1);
        for (Attribute attr : model.getEntities().get(1).getAttributes()) {
            System.out.println("ID attribute:" + attr.getIDentifiant());
        }
    }

    @Test
    void test3() {
        String src = "<Root model=\"3\"> <Model id=\"3\" /> </Root>";
        XMLAnalyser analyser = new XMLAnalyser();
        Model model = analyser.getModelFromString(src);
        assertTrue(model != null);
        assertTrue(model.getEntities().size() == 0);
    }

}