package GenCode;

import XMLIO.XMLAnalyser;
import metaModel.Model;
import org.junit.jupiter.api.Test;

public class GenCodeTest {

    @Test
    void test0() {

        XMLAnalyser analyser = new XMLAnalyser();
        Model model = analyser.getModelFromFilenamed("Exemple3.xml");
        GenCode pp = new GenCode();
        model.accept(pp);
        System.out.println(pp.result());

    }

}
