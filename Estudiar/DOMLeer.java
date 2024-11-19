package iticbcn.xifratge;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class DOMLeer {
    public static void main(String[] args) {
        try {
            // Crear una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parsear el archivo XML
            Document document = builder.parse(new File("libros.xml"));

            // Obtener el elemento raíz
            Element rootElement = document.getDocumentElement();
            System.out.println("Elemento raíz: " + rootElement.getNodeName());

            // Obtener todos los elementos <libro>
            NodeList libros = document.getElementsByTagName("libro");

            for (int i = 0; i < libros.getLength(); i++) {
                Node libro = libros.item(i);

                if (libro.getNodeType() == Node.ELEMENT_NODE) {
                    Element libroElement = (Element) libro;
                    System.out.println("\nLibro ID: " + libroElement.getAttribute("id"));

                    // Obtener y mostrar los subelementos
                    System.out.println("Título: " + libroElement.getElementsByTagName("titulo").item(0).getTextContent());
                    System.out.println("Autor: " + libroElement.getElementsByTagName("autor").item(0).getTextContent());
                    System.out.println("Precio: " + libroElement.getElementsByTagName("precio").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
