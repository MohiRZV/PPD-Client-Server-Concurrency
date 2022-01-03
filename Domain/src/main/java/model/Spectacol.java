package model;

import java.sql.Date;
import java.util.List;

public class Spectacol {
    int ID_spectacol;
    Date data_spectacol;
    String titlu;
    double pret_bilet;
    List<Integer> lista_locuri_vandute;
    double sold;

    public Spectacol(int ID_spectacol, Date data_spectacol, String titlu, double pret_bilet, List<Integer> lista_locuri_vandute) {
        this.ID_spectacol = ID_spectacol;
        this.data_spectacol = data_spectacol;
        this.titlu = titlu;
        this.pret_bilet = pret_bilet;
        this.lista_locuri_vandute = lista_locuri_vandute;
        this.sold = 0;
    }

    public Spectacol(int ID_spectacol, Date data_spectacol, String titlu, double pret_bilet) {
        this.ID_spectacol = ID_spectacol;
        this.data_spectacol = data_spectacol;
        this.titlu = titlu;
        this.pret_bilet = pret_bilet;
        this.sold = 0;
    }

    public Spectacol(Date data_spectacol, String titlu, double pret_bilet) {
        this.ID_spectacol = ID_spectacol;
        this.data_spectacol = data_spectacol;
        this.titlu = titlu;
        this.pret_bilet = pret_bilet;
        this.sold = 0;
    }

    public int getID_spectacol() {
        return ID_spectacol;
    }

    public void setID_spectacol(int ID_spectacol) {
        this.ID_spectacol = ID_spectacol;
    }

    public Date getData_spectacol() {
        return data_spectacol;
    }

    public void setData_spectacol(Date data_spectacol) {
        this.data_spectacol = data_spectacol;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public double getPret_bilet() {
        return pret_bilet;
    }

    public void setPret_bilet(double pret_bilet) {
        this.pret_bilet = pret_bilet;
    }

    public List<Integer> getLista_locuri_vandute() {
        return lista_locuri_vandute;
    }

    public void setLista_locuri_vandute(List<Integer> lista_locuri_vandute) {
        this.lista_locuri_vandute = lista_locuri_vandute;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "ID_spectacol=" + ID_spectacol +
                ", data_spectacol=" + data_spectacol +
                ", titlu='" + titlu + '\'' +
                ", pret_bilet=" + pret_bilet +
                ", sold=" + sold +
                '}';
    }
}
