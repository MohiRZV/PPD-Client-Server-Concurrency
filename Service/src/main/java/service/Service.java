package service;

import model.Spectacol;
import spectacole.SpectacolRepository;

import java.sql.Date;
import java.time.LocalDateTime;

public class Service {
    public static void main(String[] args) {
        SpectacolRepository repo = new SpectacolRepository();

        Spectacol spectacol = new Spectacol(Date.valueOf("1999-10-25"),"Aluna",69);

        repo.add(spectacol);
    }
}
