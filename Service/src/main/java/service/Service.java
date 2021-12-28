package service;

import model.Spectacol;
import model.Vanzare;
import spectacole.SpectacolRepository;
import vanzari.VanzareRepository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Service {
    public static void main(String[] args) {
        SpectacolRepository repo = new SpectacolRepository();
        VanzareRepository vanzariRepo = new VanzareRepository();

        Spectacol spectacol = new Spectacol(Date.valueOf("1999-10-25"),"Elling",100);
        Vanzare vanzare = new Vanzare(1,Date.valueOf("1999-10-25"));
        vanzare.setLista_locuri_vandute(Arrays.asList(1,8,90));

        //repo.add(spectacol);
        vanzariRepo.add(vanzare);
       // vanzariRepo.
    }
}
