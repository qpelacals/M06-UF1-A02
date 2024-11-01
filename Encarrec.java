import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Encarrec implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idEncarrec;
    private String nomClient;
    private String telefon;
    private String dataEncarrec;
    private List<Article> articles;
    private double preuTotal;

    // Constructor
    public Encarrec(int idEncarrec, String nomClient, String telefon, String dataEncarrec, List<Article> articles) {
        this.idEncarrec = idEncarrec;
        this.nomClient = nomClient;
        this.telefon = telefon;
        this.dataEncarrec = dataEncarrec;
        this.articles = articles;
        this.preuTotal = calculaPreuTotal();
    }

    // Mètodes getters i setters
    public int getIdEncarrec() {
        return idEncarrec;
    }

    public void setIdEncarrec(int idEncarrec) {
        this.idEncarrec = idEncarrec;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getDataEncarrec() {
        return dataEncarrec;
    }

    public void setDataEncarrec(String dataEncarrec) {
        this.dataEncarrec = dataEncarrec;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        this.preuTotal = calculaPreuTotal();
    }

    public double getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(double preuTotal) {
        this.preuTotal = preuTotal;
    }

    // Mètode per calcular el preu total de l'encàrrec
    public double calculaPreuTotal() {
        double total = 0;
        for (Article article : articles) {
            total += article.getPreu();
        }
        return total;
    }

    public void escSer(Encarrec encarrec, String fitxer) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fitxer, true))) {
            oos.writeObject(encarrec);
            System.out.println("Encàrrec escrit correctament al fitxer.");
        } catch (IOException e) {
            System.out.println("Error al guardar l'encàrrec");
        }
    }

    public Encarrec lecSer(File ruta) throws ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (Encarrec) ois.readObject();
        } catch (IOException e) {
            System.out.println("Error al llegir l'encàrrec");
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID de l'encàrrec: ").append(idEncarrec).append("\n");
        sb.append("Nom del client: ").append(nomClient).append("\n");
        sb.append("Telèfon: ").append(telefon).append("\n");
        sb.append("Data de l'encàrrec: ").append(dataEncarrec).append("\n");
        sb.append(String.format("%-12s %-10s %-15s %-12s%n", "Quantitat", "Unitats", "Article", "Preu Unitari"));
        sb.append("=========    =======    =======         =========\n");

        // Recorrem i afegim cada article a la cadena
        for (Article article : articles) {
            sb.append(String.format("%-12.1f %-10s %-15s %-10.2f%n",
                    article.getQuantitat(), article.getUnitat(), article.getNom(), article.getPreu()));
        }

        // Afegim el preu total de l'encàrrec
        sb.append("Preu total de l'encàrrec: ").append(String.format("%.2f", preuTotal)).append("\n");
        sb.append("--------------------------------------------------\n");

        return sb.toString();
    }
}
