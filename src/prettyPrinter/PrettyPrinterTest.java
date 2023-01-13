package prettyPrinter;

import XMLIO.XMLAnalyser;
import metaModel.Model;
import org.junit.jupiter.api.Test;

class PrettyPrinterTest {

    @Test
    void test() {
        XMLAnalyser analyser = new XMLAnalyser();
        Model model = analyser.getModelFromFilenamed("Exemple2.xml");
        PrettyPrinter pp = new PrettyPrinter();
        model.accept(pp);
        System.out.println(pp.result());
    }

}
