package model;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int nr_locuri;

    private List<Vanzare> vanzari = new ArrayList<>();
    private List<Spectacol> spectacole = new ArrayList<>();

    public List<Vanzare> getVanzari() {
        return vanzari;
    }

    public void setVanzari(List<Vanzare> vanzari) {
        this.vanzari = vanzari;
    }

    public List<Spectacol> getSpectacole() {
        return spectacole;
    }

    public void setSpectacole(List<Spectacol> spectacole) {
        this.spectacole = spectacole;
    }

    public Sala(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    public int getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }
}
