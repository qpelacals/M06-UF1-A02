package iticbcn.xifratge;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.File;

public class DOMEscribir {
    public static void main(String[] args) {
        try {
            // Crear una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Crear un nuevo documento XML
            Document document = builder.newDocument();

            // Crear el elemento raíz <libros>
            Element rootElement = document.createElement("libros");
            document.appendChild(rootElement);

            // Agregar un libro como elemento hijo
            Element libro = document.createElement("libro");
            libro.setAttribute("id", "1"); // Atributo del elemento
            rootElement.appendChild(libro);

            // Agregar subelementos del libro
            Element titulo = document.createElement("titulo");
            titulo.setTextContent("Java para principiantes");
            libro.appendChild(titulo);

            Element autor = document.createElement("autor");
            autor.setTextContent("Juan Pérez");
            libro.appendChild(autor);

            Element precio = document.createElement("precio");
            precio.setTextContent("29.99");
            libro.appendChild(precio);

            // Transformar el documento en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("libros.xml"));
            transformer.transform(source, result);

            System.out.println("Archivo XML generado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
