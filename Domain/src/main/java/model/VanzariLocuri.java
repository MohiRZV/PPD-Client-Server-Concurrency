package model;

public class VanzariLocuri {
    int id_vanzare;
    int numar_loc;

    public VanzariLocuri(int id_vanzare, int numar_loc) {
        this.id_vanzare = id_vanzare;
        this.numar_loc = numar_loc;
    }

    public int getId_vanzare() {
        return id_vanzare;
    }

    public void setId_vanzare(int id_vanzare) {
        this.id_vanzare = id_vanzare;
    }

    public int getNumar_loc() {
        return numar_loc;
    }

    public void setNumar_loc(int numar_loc) {
        this.numar_loc = numar_loc;
    }
}
