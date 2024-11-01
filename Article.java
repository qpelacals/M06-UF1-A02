import java.io.Serializable;

public class Article implements Serializable {
    private String nom;
    private double quantitat;
    private String unitat;
    private double preu;

    public Article (String nom, double quantitat, String unitat, double preu) {
        setNom(nom);
        setQuantitat(quantitat);
        setUnitat(unitat);
        setPreu(preu);
    }

    public String getUnitat() {
        return unitat;
    }
    public void setUnitat(String unitat) {
        this.unitat = unitat;
    }

    public double getQuantitat() {
        return quantitat;
    }
    public void setQuantitat(double quantitat) {
        this.quantitat = quantitat;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPreu() {return preu;}
    public void setPreu(double preu) {this.preu = preu;}

    @Override
    public String toString() {
        return super.toString();
    }

    public String toCSV() {
        return getNom() + ";" + getQuantitat() + ";" + getUnitat() + ";" ;
    }
}
