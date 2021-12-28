package service;

import model.Spectacol;
import model.Vanzare;
import spectacole.SpectacolRepository;
import vanzari.VanzareRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Service {
    SpectacolRepository repo = new SpectacolRepository();
    VanzareRepository vanzareRepository = new VanzareRepository();
    public static void main(String[] args) {
        SpectacolRepository repo = new SpectacolRepository();
        VanzareRepository vanzariRepo = new VanzareRepository();

        Spectacol spectacol = new Spectacol(Date.valueOf("1999-10-25"),"Elling",100);
        Vanzare vanzare = new Vanzare(1,Date.valueOf("1999-10-25"));
        vanzare.setLista_locuri_vandute(Arrays.asList(1,8,90));

        vanzariRepo.add(vanzare);

        System.out.println(repo.getOne(1));
    }

    public Spectacol getSpectacol(int id) {
        return repo.getOne(id);
    }

    public List<Spectacol> getAllSpectacole() {
        return (List<Spectacol>) repo.getAll();
    }

    public String reserve(List<Integer> list, int id_spectacol) {
        Vanzare vanzare = new Vanzare(id_spectacol, Date.valueOf(LocalDate.now()));

        if(repo.getOne(id_spectacol)==null) {
            return "Failed, the selected spectacol is unavailable!";
        }
        vanzare.setLista_locuri_vandute(list);

        String booked = "";
        for(int loc: list){
            if(vanzareRepository.isBooked(id_spectacol, loc)){
                booked=booked+", "+loc;
            }
        }

        if(booked.isEmpty()) {
            vanzareRepository.add(vanzare);

            return "Succeeded";
        } else {
            return "Failed, following seats are already booked: "+booked;
        }
    }

    public void nuke() {
        vanzareRepository.nuke();
    }
}
