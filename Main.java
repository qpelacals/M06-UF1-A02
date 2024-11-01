import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        boolean sortir = false; // Variable per controlar quan acabar el bucle del programa
        boolean esEncarrec = false;
        List<Encarrec> encarrecs = new ArrayList<>();
        String nomClient;
        String telefonClient;
        String dataClient;
        List<Article> articles;

        try (BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in))) {
            while (!sortir) { // Bucle principal del programa
                int idEncarrec = 0;
                System.out.print("Què vols fer?\na. Generar un nou encàrrec\nb. Mostrar un encàrrec\nc. Editar un encàrrec\nd. Sortir\n");

                String variable1 = reader1.readLine();
                switch (variable1) {
                    case "a" -> {

                        while (true) { // Bucle per afegir múltiples encarrecs
                            idEncarrec++;
                            // Recollida de les dades bàsiques del client
                            System.out.print("\nNom: ");
                            nomClient = reader1.readLine();
                            System.out.print("Telèfon: ");
                            telefonClient = reader1.readLine();
                            System.out.print("Data d'enllestiment: ");
                            dataClient = reader1.readLine();


                            articles = new ArrayList<>(); // Llista per emmagatzemar els articles de l'encàrrec
                            while (true) { // Bucle per afegir múltiples articles
                                System.out.print("Nom de l'article: ");
                                String nomArticle = reader1.readLine();
                                System.out.print("Quantitat: ");
                                double quantitat = Double.parseDouble(reader1.readLine());
                                System.out.print("Unitat: ");
                                String unitat = reader1.readLine();
                                System.out.print("Preu unitari de l'article: ");
                                double preuArticle = Double.parseDouble(reader1.readLine());

                                Article article = new Article(nomArticle, quantitat, unitat, preuArticle);
                                articles.add(article); // Afegir l'article a la llista d'encàrrecs

                                System.out.print("Vols afegir un altre article? (s/n): ");
                                String resposta = reader1.readLine();
                                if (resposta.equalsIgnoreCase("n")) { // Si l'usuari diu que no, surt del bucle
                                    break;
                                }
                            }
                            Encarrec encarrec = new Encarrec(idEncarrec, nomClient, telefonClient, dataClient, articles);
                            encarrecs.add(encarrec);
                            System.out.print("Vols afegir un altre encarrec? (s/n): ");
                            String resposta = reader1.readLine();
                            if (resposta.equalsIgnoreCase("n")) { // Si l'usuari diu que no, surt del bucle
                                break;
                            } else {
                                esEncarrec = true;
                            }
                        }

                        if (esEncarrec) {
                            // Preguntar a l'usuari quin tipus de fitxer vol generar
                            System.out.println("Quin tipus de fitxer vols generar?\na. Serialitzat\nb. Accés Aleatori\n");
                            String tipusFitxer = reader1.readLine();
                            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            String fileName = "C:\\Users\\accesadades\\" + "encarrecs_client" + timestamp + "_" + System.currentTimeMillis() + ".dat";
                            switch (tipusFitxer) {
                                case "a" -> {
                                    try {
                                        //cal especificar el nom del fitxer
                                        ObjectOutputStream serializador = new ObjectOutputStream(new FileOutputStream(fileName));
                                        serializador.writeObject(encarrecs);
                                        serializador.close();
                                    } catch (IOException ioe) {
                                        System.out.print("Error al generar el fitxer serialitzable: " + ioe.getMessage());
                                    }
                                }
                                case "b" -> {

                                    try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
                                        for (Encarrec encarrec : encarrecs) {
                                            int longRecord = 0;

                                            // Escrivim l'ID de l'encàrrec
                                            raf.writeInt(encarrec.getIdEncarrec());
                                            longRecord += Integer.BYTES; // Cada enter ocupa 4 bytes

                                            // Escrivim el nom del client com a UTF (sense longitud fixa)
                                            raf.writeUTF(encarrec.getNomClient());
                                            longRecord += 2 + encarrec.getNomClient().length() * 2; // UTF ocupa 2 bytes per caràcter + 2 bytes d'encapçalament

                                            // Escrivim el telèfon del client com a UTF
                                            raf.writeUTF(encarrec.getTelefon());
                                            longRecord += 2 + encarrec.getTelefon().length() * 2;

                                            // Escrivim la data de l'encàrrec com a UTF
                                            raf.writeUTF(encarrec.getDataEncarrec());
                                            longRecord += 2 + encarrec.getDataEncarrec().length() * 2;

                                            // Iterem per la llista d'articles de l'encàrrec i escrivim cada article
                                            for (Article article : encarrec.getArticles()) {
                                                // Escrivim el nom de l'article com a UTF
                                                raf.writeUTF(article.getNom());
                                                longRecord += 2 + article.getNom().length() * 2;

                                                // Escrivim la quantitat de l'article com a doble
                                                raf.writeDouble(article.getQuantitat());
                                                longRecord += Double.BYTES;

                                                // Escrivim la unitat de l'article com a UTF
                                                raf.writeUTF(article.getUnitat());
                                                longRecord += 2 + article.getUnitat().length() * 2;

                                                // Escrivim el preu de l'article
                                                raf.writeDouble(article.getPreu());
                                                longRecord += Double.BYTES;
                                            }

                                            // Afegeix un delimitador de final de llista per els articles
                                            raf.writeUTF("/"); // Marquem el final de la llista amb "/"
                                            longRecord += 2; // Cada UTF ocupa 2 bytes d'encapçalament

                                            // Escrivim el preu total de l'encàrrec com a doble
                                            raf.writeDouble(encarrec.getPreuTotal());
                                            longRecord += Double.BYTES;

                                            // Escrivim la longitud total del registre al final
                                            raf.writeInt(longRecord);
                                        }
                                        System.out.println("Fitxer d'accés aleatori creat.");
                                    } catch (IOException e) {
                                        System.out.println("Error al generar el fitxer aleatori: " + e.getMessage());
                                    }
                                }
                            }
                        } else {
                            // Preguntar a l'usuari quin tipus de fitxer vol generar
                            System.out.println("Quin tipus de fitxer vols generar?\na. Albarà\nb. CSV\nc. Binari\n");
                            String tipusFitxer = reader1.readLine();
                            switch (tipusFitxer) {
                                case "a" -> {
                                    // Generació del fitxer de text en format albarà
                                    String fileName = "C:\\Users\\accesadades\\" + "encarrecs_client_" + nomClient + "_" + System.currentTimeMillis() + ".txt";

                                    StringBuilder albara = new StringBuilder(); // Crear l'albarà
                                    albara.append(String.format("Nom del client: %s\n", nomClient));
                                    albara.append(String.format("Telefon del client: %s\n", telefonClient));
                                    albara.append(String.format("Data de l'encarrec: %s\n", dataClient));
                                    albara.append(String.format("%-12s %-10s %-15s\n", "Quantitat", "Unitats", "Article"));
                                    albara.append("\n=========    =======    =======\n");
                                    for (Article article : articles) {
                                        albara.append(String.format("%-12.1f %-10s %-15s\n", article.getQuantitat(), article.getUnitat(), article.getNom()));
                                    }

                                    try {
                                        // Escriure l'albarà al fitxer
                                        File f1 = new File(fileName);
                                        f1.createNewFile();
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                                        writer.write(albara.toString());
                                        writer.close();
                                        System.out.println("Fitxer de text creat: " + fileName);
                                    } catch (FileNotFoundException fn) {
                                        System.out.println("No es troba el fitxer");
                                    } catch (IOException io) {
                                        System.out.println("Error d'E/S");
                                    }
                                }
                                case "b" -> {
                                    // Generació del fitxer en format CSV
                                    String fileName = "C:\\Users\\accesadades\\" + "encarrecs_client_" + nomClient + "_" + System.currentTimeMillis() + ".csv";

                                    StringBuilder csv = new StringBuilder();
                                    csv.append(String.format("%s;", nomClient));
                                    csv.append(String.format("%s;", telefonClient));
                                    csv.append(String.format("%s;", dataClient));
                                    for (Article article : articles) {
                                        csv.append(article.toCSV());
                                    }

                                    try {
                                        // Escriure el CSV al fitxer
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                                        writer.write(csv.toString());
                                        writer.close();
                                        System.out.println("Fitxer csv creat: " + fileName);
                                    } catch (FileNotFoundException fn) {
                                        System.out.println("No es troba el fitxer");
                                    } catch (IOException io) {
                                        System.out.println("Error d'E/S");
                                    }
                                }
                                case "c" -> {
                                    // Generació del fitxer en format binari
                                    String fileName = "C:\\Users\\accesadades\\" + "encarrecs_client_" + nomClient + "_" + System.currentTimeMillis() + ".bin";

                                    try (FileOutputStream fileStr = new FileOutputStream(fileName);
                                         DataOutputStream dataOut = new DataOutputStream(fileStr)) {

                                        // Escriure les dades del client en format binari
                                        dataOut.writeUTF(nomClient);
                                        dataOut.writeUTF(telefonClient);
                                        dataOut.writeUTF(dataClient);

                                        // Escriure els articles de l'encàrrec en format binari
                                        dataOut.writeInt(articles.size());
                                        for (Article article : articles) {
                                            dataOut.writeUTF(article.getNom());
                                            dataOut.writeDouble(article.getQuantitat());
                                            dataOut.writeUTF(article.getUnitat());
                                        }

                                        System.out.println("Fitxer binari creat: " + fileName);

                                    } catch (FileNotFoundException e) {
                                        System.out.println("El fitxer no s'ha trobat.");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    case "b" -> {
                        // Opció per obrir un fitxer i mostrar-ne el contingut
                        System.out.println("Quin tipus de fitxer vols obrir?\na. Albarà\nb. CSV\nc. Binari\nd. Serialitzat\ne. Accés Aleatori\n");
                        String tipusFitxer = reader1.readLine();
                        System.out.print("Introdueix la ruta del fitxer: ");
                        String rutaFitxer = reader1.readLine();

                        switch (tipusFitxer) {
                            case "b" -> {
                                // Lectura d'un fitxer CSV
                                try (BufferedReader br = new BufferedReader(new FileReader(rutaFitxer))) {
                                    String[] clientInfo = br.readLine().split(";");
                                    nomClient = clientInfo[0];
                                    telefonClient = clientInfo[1];
                                    dataClient = clientInfo[2];

                                    System.out.printf("Nom del client: %s%n", nomClient);
                                    System.out.printf("Telefon del client: %s%n", telefonClient);
                                    System.out.printf("Data de l'encarrec: %s%n", dataClient);
                                    System.out.printf("%-12s %-10s %-15s%n", "Quantitat", "Unitats", "Article");
                                    System.out.println("=========    =======    =======");

                                    // Llegir i mostrar els articles
                                    for (int i = 3; i < clientInfo.length; i += 3) {
                                        double quantitat = Double.parseDouble(clientInfo[i + 1]);
                                        String unitat = clientInfo[i + 2];
                                        String nomArticle = clientInfo[i];

                                        System.out.printf("%-12.1f %-10s %-15s%n", quantitat, unitat, nomArticle);
                                    }
                                } catch (FileNotFoundException e) {
                                    System.out.println("El fitxer no s'ha trobat.");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "c" -> {
                                // Lectura d'un fitxer binari
                                try (FileInputStream fileStr = new FileInputStream(rutaFitxer);
                                     DataInputStream dataIn = new DataInputStream(fileStr)) {

                                    // Llegir les dades del client
                                    nomClient = dataIn.readUTF();
                                    telefonClient = dataIn.readUTF();
                                    dataClient = dataIn.readUTF();

                                    System.out.printf("Nom del client: %s%n", nomClient);
                                    System.out.printf("Telefon del client: %s%n", telefonClient);
                                    System.out.printf("Data de l'encarrec: %s%n", dataClient);
                                    System.out.printf("%-12s %-10s %-15s%n", "Quantitat", "Unitats", "Article");
                                    System.out.println("=========    =======    =======");

                                    // Llegir els articles
                                    int numArticles = dataIn.readInt();
                                    for (int i = 0; i < numArticles; i++) {
                                        String nomArticle = dataIn.readUTF();
                                        double quantitat = dataIn.readDouble();
                                        String unitat = dataIn.readUTF();

                                        System.out.printf("%-12.1f %-10s %-15s%n", quantitat, unitat, nomArticle);
                                    }

                                } catch (FileNotFoundException e) {
                                    System.out.println("El fitxer no s'ha trobat.");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "d" -> {
                                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaFitxer))) {
                                    encarrecs = (List<Encarrec>) ois.readObject();

                                    // Recorrem cada encàrrec i el mostrem amb el format específic de toString
                                    for (Encarrec encarrec : encarrecs) {
                                        System.out.println(encarrec);
                                    }
                                } catch (IOException | ClassNotFoundException e) {
                                    System.out.println("Error al llegir el fitxer serialitzat: " + e.getMessage());
                                }
                            }
                            case "e" -> {
                                try (RandomAccessFile raf = new RandomAccessFile(rutaFitxer, "r")) {
                                    while (raf.getFilePointer() < raf.length()) {
                                        int id = raf.readInt();
                                        String nom = raf.readUTF();
                                        String telefon = raf.readUTF();
                                        String dataEncarrec = raf.readUTF();

                                        List<Article> llistaArticles = new ArrayList<>();
                                        while (true) {
                                            String nomArticle = raf.readUTF();
                                            if (nomArticle.equals("/")) break;
                                            double quantitat = raf.readDouble();
                                            String unitat = raf.readUTF();
                                            double preu = raf.readDouble();
                                            llistaArticles.add(new Article(nomArticle, quantitat, unitat, preu));
                                        }

                                        double preuTotal = raf.readDouble();
                                        Encarrec encarrec = new Encarrec(id, nom, telefon, dataEncarrec, llistaArticles);
                                        encarrec.setPreuTotal(preuTotal);

                                        System.out.println(encarrec.toString());
                                        System.out.println("--------------------------------------------------");
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error al llegir el fitxer d'accés aleatori: " + e.getMessage());
                                }
                            }

                        }
                    }
                    case "c" -> {
                        System.out.print("Introdueix la ruta del fitxer: ");
                        String rutaFitxer = reader1.readLine();
                        System.out.print("Introdueix l'ID de l'encàrrec a modificar: ");
                        int idModificar = Integer.parseInt(reader1.readLine());

                        // Sol·licitem les noves dades
                        System.out.print("Introdueix el nou telèfon: ");
                        String nouTelefon = reader1.readLine();
                        System.out.print("Introdueix la nova data d'encàrrec: ");
                        String novaData = reader1.readLine();

                        try (RandomAccessFile raf = new RandomAccessFile(rutaFitxer, "rw")) {
                            boolean trobat = false;

                            // Bucle per buscar el registre amb l'ID indicat
                            while (raf.getFilePointer() < raf.length()) {
                                long posicioInicial = raf.getFilePointer(); // Guardem la posició del registre
                                int idActual = raf.readInt(); // Llegim l'ID de l'encàrrec

                                // Comprovem si l'ID coincideix amb l'ID que volem modificar
                                if (idActual == idModificar) {
                                    trobat = true;

                                    // Saltar el nom del client perquè no necessitem modificar-lo
                                    raf.readUTF();

                                    // Actualitzem el telèfon
                                    raf.writeUTF(nouTelefon);

                                    // Actualitzem la data de l'encàrrec
                                    raf.writeUTF(novaData);

                                    System.out.println("Encàrrec actualitzat correctament.");
                                    break;
                                }

                                // Saltar el registre si no coincideix amb l'ID indicat
                                raf.readUTF(); // Nom del client
                                raf.readUTF(); // Telèfon (llegit però saltat)
                                raf.readUTF(); // Data de l'encàrrec (llegida però saltada)

                                // Saltar la llista d'articles i el preu total
                                while (true) {
                                    String nomArticle = raf.readUTF();
                                    if (nomArticle.equals("/")) break; // Final de la llista d'articles
                                    raf.readDouble(); // Quantitat
                                    raf.readUTF();    // Unitat
                                    raf.readDouble(); // Preu
                                }

                                raf.readDouble(); // Preu total
                                raf.readInt(); // Longitud del registre (final del registre)
                            }

                            // Missatge si l'encàrrec no s'ha trobat
                            if (!trobat) {
                                System.out.println("Encàrrec amb l'ID indicat no trobat.");
                            }
                        } catch (IOException e) {
                            System.out.println("Error al modificar l'encàrrec: " + e.getMessage());
                        }
                    }
                    case "d" -> {
                        sortir = true; // Sortir del bucle principal
                        System.out.println("Sortint del programa... Fins aviat!");
                    }
                    default -> System.out.println("Tria una de les opcions proposades: a, b o c"); // Missatge per a opcions no vàlides
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Tractament d'errors d'entrada/sortida
        }
    }
}