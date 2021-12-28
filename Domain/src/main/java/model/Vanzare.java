package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Vanzare {
    int ID_vanzare;
    int ID_spectacol;
    Date data_vanzare;
    int nr_bilete_vandute;
    List<Integer> lista_locuri_vandute;
    double suma;

    public Vanzare(int ID_spectacol, Date data_vanzare, int nr_bilete_vandute, List<Integer> lista_locuri_vandute, double suma) {
        this.ID_spectacol = ID_spectacol;
        this.data_vanzare = data_vanzare;
        this.nr_bilete_vandute = nr_bilete_vandute;
        this.lista_locuri_vandute = lista_locuri_vandute;
        this.suma = suma;
    }

    public Vanzare(int ID_vanzare, int ID_spectacol, Date data_vanzare) {
        this.ID_vanzare = ID_vanzare;
        this.ID_spectacol = ID_spectacol;
        this.data_vanzare = data_vanzare;
        this.lista_locuri_vandute = new ArrayList<>();
    }


    public Vanzare(int ID_spectacol, Date data_vanzare) {
        this.ID_spectacol = ID_spectacol;
        this.data_vanzare = data_vanzare;
        this.lista_locuri_vandute = new ArrayList<>();

    }

    public int getID_vanzare() {
        return ID_vanzare;
    }

    public void setID_vanzare(int ID_vanzare) {
        this.ID_vanzare = ID_vanzare;
    }

    public int getID_spectacol() {
        return ID_spectacol;
    }

    public void setID_spectacol(int ID_spectacol) {
        this.ID_spectacol = ID_spectacol;
    }

    public Date getData_vanzare() {
        return data_vanzare;
    }

    public void setData_vanzare(Date data_vanzare) {
        this.data_vanzare = data_vanzare;
    }

    public int getNr_bilete_vandute() {
        return nr_bilete_vandute;
    }

    public void setNr_bilete_vandute(int nr_bilete_vandute) {
        this.nr_bilete_vandute = nr_bilete_vandute;
    }

    public List<Integer> getLista_locuri_vandute() {
        return lista_locuri_vandute;
    }

    public void setLista_locuri_vandute(List<Integer> lista_locuri_vandute) {
        this.lista_locuri_vandute = lista_locuri_vandute;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

}
