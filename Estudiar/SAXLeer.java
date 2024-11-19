package iticbcn.xifratge;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.File;

public class SAXLeer {
    public static void main(String[] args) {
        try {
            // Crear una instancia de SAXParserFactory
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Definir el manejador para los eventos SAX
            DefaultHandler handler = new DefaultHandler() {
                boolean esTitulo = false;
                boolean esAutor = false;
                boolean esPrecio = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase("libro")) {
                        System.out.println("\nLibro ID: " + attributes.getValue("id"));
                    } else if (qName.equalsIgnoreCase("titulo")) {
                        esTitulo = true;
                    } else if (qName.equalsIgnoreCase("autor")) {
                        esAutor = true;
                    } else if (qName.equalsIgnoreCase("precio")) {
                        esPrecio = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    if (esTitulo) {
                        System.out.println("Título: " + new String(ch, start, length));
                        esTitulo = false;
                    } else if (esAutor) {
                        System.out.println("Autor: " + new String(ch, start, length));
                        esAutor = false;
                    } else if (esPrecio) {
                        System.out.println("Precio: " + new String(ch, start, length));
                        esPrecio = false;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    // Podemos implementar lógica al final de un elemento si es necesario
                }
            };

            // Parsear el archivo XML
            saxParser.parse(new File("libros.xml"), handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

