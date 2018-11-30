package persistencia;

import model.*;
import java.io.File;
import java.util.Iterator;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import principal.GestioExcursionsExcepcio;

/**
 *
 * @author FTA
 */
public class GestorXML implements ProveedorPersistencia {

    private Document doc;
    private Desti desti;

    public Desti getDesti() {
        return desti;
    }

    public void setDesti(Desti desti) {
        this.desti = desti;
    }

    @Override
    public void desarDesti(String nomFitxer, Desti desti) throws GestioExcursionsExcepcio {
        construeixModel(desti);
        desarModel(nomFitxer);
    }

    @Override
    public void carregarDesti(String nomFitxer) throws GestioExcursionsExcepcio {
        carregarFitxer(nomFitxer);
        fitxerDesti();
    }

    /*Paràmetres: Destí a partir de la qual volem construir el model
     *
     *Acció: 
     * - Llegir els atributs de l'objecte Desti passat per paràmetre 
     *   per construir un model (document XML) sobre el Document doc (atribut de GestorXML).
     *
     * - L'arrel del document XML és "desti". Aquesta arrel, l'heu d'afegir 
     *   a doc. Un cop fet això, heu de recórrer l'ArrayList components de 
     *   Desti i per a cada component, afegir un fill a doc. Cada fill 
     *   tindrà com atributs els atributs de l'objecte (codi, nom, …).
     *
     * - Si es tracta d'una excursió, a més, heu d'afegir fills addicionals amb 
     *   les visites de lliure pagament, les visites de pagament i els guies assignats a excursió .
     *
     *Retorn: cap
     */
    public void construeixModel(Desti desti) throws GestioExcursionsExcepcio {
        //Es construeix el document model
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new GestioExcursionsExcepcio("GestorXML.model");
        }

        this.doc = (Document) builder.newDocument();

        //Element arrel
        Element arrel = doc.createElement("desti");
        arrel.setAttribute("codi", String.valueOf(desti.getCodi()));
        arrel.setAttribute("nom", desti.getNom());
        arrel.setAttribute("continent", desti.getContinent());
        doc.appendChild(arrel);

        for (int i = 0; i < desti.getComponents().size(); i++) {

            if (desti.getComponents().get(i) instanceof VisitaLliure) {

                Element fill = doc.createElement("visitaLliure");

                fill.setAttribute("codi", ((VisitaLliure) desti.getComponents().get(i)).getCodi());
                fill.setAttribute("nom", ((VisitaLliure) desti.getComponents().get(i)).getNom());
                fill.setAttribute("adreca", ((VisitaLliure) desti.getComponents().get(i)).getAdreca());
                fill.setAttribute("coordenades", ((VisitaLliure) desti.getComponents().get(i)).getCoordenades());
                fill.setAttribute("durada", String.valueOf(((VisitaLliure) desti.getComponents().get(i)).getDurada()));

                arrel.appendChild(fill);

            } else if (desti.getComponents().get(i) instanceof VisitaPagament) {

                Element fill = doc.createElement("visitaPagament");

                fill.setAttribute("codi", ((VisitaPagament) desti.getComponents().get(i)).getCodi());
                fill.setAttribute("nom", ((VisitaPagament) desti.getComponents().get(i)).getNom());
                fill.setAttribute("adreca", ((VisitaPagament) desti.getComponents().get(i)).getAdreca());
                fill.setAttribute("coordenades", ((VisitaPagament) desti.getComponents().get(i)).getCoordenades());
                fill.setAttribute("durada", String.valueOf(((VisitaPagament) desti.getComponents().get(i)).getDurada()));
                fill.setAttribute("preu", String.valueOf(((VisitaPagament) desti.getComponents().get(i)).getPreu()));

                arrel.appendChild(fill);

            } else if (desti.getComponents().get(i) instanceof Guia) {

                Element fill = doc.createElement("guia");

                fill.setAttribute("codi", ((Guia) desti.getComponents().get(i)).getCodi());
                fill.setAttribute("nom", ((Guia) desti.getComponents().get(i)).getNom());
                fill.setAttribute("adreca", ((Guia) desti.getComponents().get(i)).getAdreca());
                fill.setAttribute("telefon", ((Guia) desti.getComponents().get(i)).getTelefon());

                arrel.appendChild(fill);

            } else if (desti.getComponents().get(i) instanceof Excursio) { //És excursió

                Element fill = doc.createElement("excursio");

                fill.setAttribute("codi", ((Excursio) desti.getComponents().get(i)).getCodi());
                fill.setAttribute("nom", ((Excursio) desti.getComponents().get(i)).getNom());
                fill.setAttribute("preu", String.valueOf(((Excursio) desti.getComponents().get(i)).getPreu()));

                Iterator codi = ((Excursio) desti.getComponents().get(i)).getComponents().keySet().iterator();

                while (codi.hasNext()) {

                    Object codiActual = codi.next();

                    if (((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual) instanceof VisitaLliure) {

                        Element net = doc.createElement("visitaLliure");

                        net.setAttribute("codi", ((VisitaLliure) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getCodi());
                        net.setAttribute("nom", ((VisitaLliure) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getNom());
                        net.setAttribute("adreca", ((VisitaLliure) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getAdreca());
                        net.setAttribute("coordenades", ((VisitaLliure) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getCoordenades());
                        net.setAttribute("durada", String.valueOf(((VisitaLliure) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getDurada()));

                        fill.appendChild(net);

                    } else if (((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual) instanceof VisitaPagament) {

                        Element net = doc.createElement("visitaPagament");

                        net.setAttribute("codi", ((VisitaPagament) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getCodi());
                        net.setAttribute("nom", ((VisitaPagament) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getNom());
                        net.setAttribute("adreca", ((VisitaPagament) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getAdreca());
                        net.setAttribute("coordenades", ((VisitaPagament) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getCoordenades());
                        net.setAttribute("durada", String.valueOf(((VisitaPagament) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getDurada()));
                        net.setAttribute("preu", String.valueOf(((VisitaPagament) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getPreu()));

                        fill.appendChild(net);

                    } else if (((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual) instanceof Guia) {

                        Element net = doc.createElement("guia");

                        net.setAttribute("codi", ((Guia) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getCodi());
                        net.setAttribute("nom", ((Guia) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getNom());
                        net.setAttribute("adreca", ((Guia) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getAdreca());
                        net.setAttribute("telefon", ((Guia) ((Excursio) desti.getComponents().get(i)).getComponents().get(codiActual)).getTelefon());

                        fill.appendChild(net);

                    }
                }
                arrel.appendChild(fill);
            }

        }
    }

    public void desarModel(String nomFitxer) throws GestioExcursionsExcepcio {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            File f = new File(nomFitxer + ".xml");
            StreamResult result = new StreamResult(f);
            transformer.transform(source, result);
        } catch (Exception ex) {
            throw new GestioExcursionsExcepcio("GestorXML.desar");
        }
    }

    public void carregarFitxer(String nomFitxer) throws GestioExcursionsExcepcio {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            File f = new File(nomFitxer + ".xml");
            doc = builder.parse(f);
        } catch (Exception ex) {
            throw new GestioExcursionsExcepcio("GestorXML.carrega");
        }
    }

    /*Paràmetres: cap
     *
     *Acció: 
     * - Llegir el fitxer del vostre sistema i carregar-lo sobre l'atribut doc, 
     *   per assignar valors als atributs de Desti i la resta d'objectes que formen 
     *   els components de Desti.
     *    
     * - Primer heu de crear l'objecte Desti a partir de l'arrel del document XML
     *   per després recórrer el doc i per cada fill, afegir un objecte a l'atribut 
     *   components de Desti mitjançant el mètode escaient de la classe Desti.
    
     * - Si l'element del document XML que s'ha llegit és una excursió, recordeu 
     *   que a més d'afegir-los a components, també haureu d'afegir en l'excursió 
     *   les visites lliures de pagament, les visites de pagament i els guies de
     *   l'excursió.
     *
     *Retorn: cap
     */
    private void fitxerDesti() throws GestioExcursionsExcepcio {

        String component;

        Element arrel = doc.getDocumentElement();

        //Es crea l'objecte desti
        desti = new Desti(arrel.getAttribute("nom"), arrel.getAttribute("continent"), Integer.parseInt(arrel.getAttribute("codi")));

        //Recorregut de nodes fill d'un element       
        NodeList llistaFills = arrel.getChildNodes();

        for (int i = 0; i < llistaFills.getLength(); i++) {

            Node fill = llistaFills.item(i);

            if (fill.getNodeType() == Node.ELEMENT_NODE) {

                component = fill.getNodeName();

                if (component.equals("visitaLliure")) {

                    String codi = ((Element) fill).getAttribute("codi");
                    String nom = ((Element) fill).getAttribute("nom");
                    String adreca = ((Element) fill).getAttribute("adreca");
                    String coordenades = ((Element) fill).getAttribute("coordenades");
                    int durada = Integer.parseInt(((Element) fill).getAttribute("durada"));

                    desti.addVisitaLliure(new VisitaLliure(codi, nom, adreca, coordenades, durada));

                } else if (component.equals("visitaPagament")) {

                    String codi = ((Element) fill).getAttribute("codi");
                    String nom = ((Element) fill).getAttribute("nom");
                    String adreca = ((Element) fill).getAttribute("adreca");
                    String coordenades = ((Element) fill).getAttribute("coordenades");
                    int durada = Integer.parseInt(((Element) fill).getAttribute("durada"));
                    double preu = Double.parseDouble(((Element) fill).getAttribute("preu"));

                    desti.addVisitaPagament(new VisitaPagament(codi, nom, adreca, coordenades, durada, preu));

                } else if (component.equals("guia")) {

                    String codi = ((Element) fill).getAttribute("codi");
                    String nom = ((Element) fill).getAttribute("nom");
                    String adreca = ((Element) fill).getAttribute("adreca");
                    String telefon = ((Element) fill).getAttribute("telefon");

                    desti.addGuia(new Guia(codi, nom, adreca, telefon));

                } else if (component.equals("excursio")) { //És excursió

                    String codi = ((Element) fill).getAttribute("codi");
                    String nom = ((Element) fill).getAttribute("nom");
                    double preu = Double.parseDouble(((Element) fill).getAttribute("preu"));;

                    Excursio excursioActual = new Excursio(codi, nom, preu);

                    desti.addExcursio(excursioActual);

                    NodeList nets = fill.getChildNodes();

                    for (int j = 0; j < nets.getLength(); j++) {

                        Node net = nets.item(j);

                        if (net!=null && net.getNodeType() == Node.ELEMENT_NODE) {

                            int tipus = 0;

                            component = net.getNodeName();

                            switch (component) {
                                case "guia":
                                    tipus = 1;
                                    break;
                                case "visitaLliure":
                                    tipus = 2;
                                    break;
                                default: //VisitaPagament
                                    tipus = 3;
                                    break;
                            }

                            int pos = desti.selectComponent(tipus, ((Element) net).getAttribute("codi"));
                            excursioActual.addComponent(desti.getComponents().get(pos));

                        }
                    }
                }
            }
        }
    }
}
