package service;

import model.Sala;
import model.Spectacol;
import model.Vanzare;
import spectacole.SpectacolRepository;
import vanzari.VanzareRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    SpectacolRepository repo = new SpectacolRepository();
    VanzareRepository vanzareRepository = new VanzareRepository();

    public static void main(String[] args) {
        System.out.println(Service.getInstance().report((List<Vanzare>) Service.getInstance().vanzareRepository.getAll()));
    }

    public synchronized List<Vanzare> getAllVanzari() {
        return (List<Vanzare>) vanzareRepository.getAll();
    }

    //pentru fiecare spectacol se calculeaza locuri vandute, numarul acestora si suma totala achitatas
    public Map<Integer, Vanzare> report(List<Vanzare> vanzari) {
        Map<Integer, Vanzare> totalSales = new HashMap<>();
        for(int i=1;i<=3;i++) {
            for(Vanzare vanzare : vanzari){
                if(vanzare.getID_spectacol()==i) {
                    if(totalSales.get(i)==null){
                        totalSales.put(i,new Vanzare(vanzare.getID_spectacol(), Date.valueOf(LocalDate.now())));
                        Vanzare current = totalSales.get(i);
                        current.setNr_bilete_vandute(vanzare.getNr_bilete_vandute());
                        current.setLista_locuri_vandute(vanzare.getLista_locuri_vandute());
                        current.setSuma(vanzare.getSuma());
                    }else{
                        Vanzare current = totalSales.get(i);
                        current.setNr_bilete_vandute(current.getNr_bilete_vandute()+vanzare.getNr_bilete_vandute());
                        List<Integer> list = current.getLista_locuri_vandute();
                        list.addAll(vanzare.getLista_locuri_vandute());
                        current.setLista_locuri_vandute(list);
                        current.setSuma(vanzare.getSuma()+current.getSuma());
                    }
                }
            }
        }
        return totalSales;
    }


    private Service(){}

    private static Service instance;
    public static Service getInstance() {
        if(instance == null){
            instance = new Service();
        }
        return instance;
    }

    public Spectacol getSpectacol(int id) {
        return repo.getOne(id);
    }

    public List<Spectacol> getAllSpectacole() {
        return (List<Spectacol>) repo.getAll();
    }

    public String reserve(List<Integer> list, int id_spectacol, Sala sala) {
        Vanzare vanzare = new Vanzare(id_spectacol, Date.valueOf(LocalDate.now()));

        if(repo.getOne(id_spectacol)==null) {
            return "Failed, the selected spectacol is unavailable!";
        }
        vanzare.setLista_locuri_vandute(list);
        vanzare.setNr_bilete_vandute(list.size());
        //la fiecare vanzare se calculeaza suma achitata
        vanzare.setSuma(sala.getSpectacole().stream()
                .filter(spectacol -> spectacol.getID_spectacol()==vanzare.getID_spectacol())
                .findFirst()
                .get().getPret_bilet() * vanzare.getNr_bilete_vandute());

        int booked = vanzareRepository.isBooked(id_spectacol, list);

        if(booked==0) {
            //daca toate locurile sunt disponibile se creeaza vanzarea
            vanzareRepository.add(vanzare);
            sala.getVanzari().add(vanzare);
            return "Succeeded";
        } else {
            return "Failed, "+booked+" of the selected seats are already booked ";
        }
    }

    public void evidenta() {
        Iterable<Vanzare> vanzari = vanzareRepository.getAll();
        System.out.println(vanzari);
    }

    public void nuke() {
        vanzareRepository.nuke();
    }
}
