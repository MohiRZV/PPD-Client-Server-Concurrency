package service;

import model.Spectacol;
import model.Vanzare;
import spectacole.SpectacolRepository;
import vanzari.VanzareRepository;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Service {
    SpectacolRepository repo = new SpectacolRepository();
    public static void main(String[] args) {
        SpectacolRepository repo = new SpectacolRepository();
        VanzareRepository vanzariRepo = new VanzareRepository();

        Spectacol spectacol = new Spectacol(Date.valueOf("1999-10-25"),"Elling",100);
        Vanzare vanzare = new Vanzare(1,Date.valueOf("1999-10-25"));
        vanzare.setLista_locuri_vandute(Arrays.asList(1,8,90));

        vanzariRepo.add(vanzare);

//        Spectacol spectacol = new Spectacol(Date.valueOf("1999-10-25"),"Aluna",69);
//
//        repo.add(spectacol);
        System.out.println(repo.getOne(1));
    }

    public Spectacol getSpectacol(int id) {
        return repo.getOne(id);
    }

    public List<Spectacol> getAllSpectacole() {
        return (List<Spectacol>) repo.getAll();
    }
}
