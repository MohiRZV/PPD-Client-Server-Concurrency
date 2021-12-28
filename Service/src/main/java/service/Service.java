package service;

import model.Spectacol;
import spectacole.SpectacolRepository;

import java.sql.Date;
import java.util.List;

public class Service {
    SpectacolRepository repo = new SpectacolRepository();
    public static void main(String[] args) {
        SpectacolRepository repo = new SpectacolRepository();
//
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
