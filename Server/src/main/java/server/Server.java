package server;

import model.Sala;
import model.Vanzare;
import service.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server{

    private Service service = Service.getInstance();
    private Sala sala = new Sala(100);

    int nrRaport = 1;

    boolean isRunning = true;

    static final int MAX_T = 3;
    ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
    private Server() {}
    private static Server instance;
    public static Server getInstance() {
        if(instance==null){
            instance=new Server();
            Thread thread = new Thread(){
                public void run(){
                    instance.run();
                }
            };

            thread.start();
        }
        return instance;
    }

    private boolean verify(Map<Integer, Vanzare> inMemory, Map<Integer, Vanzare> inDB) {

        AtomicBoolean isCorrect = new AtomicBoolean(true);
        inMemory.forEach((integer, vanzare) ->{
            if(!vanzare.getLista_locuri_vandute().containsAll(inDB.get(integer).getLista_locuri_vandute())) {
                isCorrect.set(false);
                System.out.println("wrong");
                System.out.println(inDB.get(integer).getLista_locuri_vandute());
                System.out.println(vanzare.getLista_locuri_vandute());
            }
        });

        return isCorrect.get();
    }

    private synchronized void showReport() {
        Map<Integer, Vanzare> vanzari = service.report(sala.getVanzari());

        boolean isCorrect = verify(vanzari, service.report(service.getAllVanzari()));

        System.out.println("Situatie "+nrRaport+": ");
        vanzari.forEach((id, vanzare)->{
            System.out.println("Pentru spectacolul "+id);
            System.out.println("Au fost vandute locurile: "+vanzare.getLista_locuri_vandute());
            System.out.println("In numar de: "+vanzare.getNr_bilete_vandute());
            System.out.println("Insumand "+vanzare.getSuma());
        });

        System.out.println("Raportul efectuat a fost corect: "+isCorrect);
    }

    public void run() {
       long startTime = System.nanoTime();

       sala.setSpectacole(service.getAllSpectacole());
       long lifeSpan = TimeUnit.NANOSECONDS.convert(1, TimeUnit.MINUTES);
       long sleepTime = TimeUnit.MILLISECONDS.convert(5,TimeUnit.SECONDS);

       while(System.nanoTime()-startTime<lifeSpan) {

           showReport();

           try {
               Thread.sleep(sleepTime);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

       isRunning = false;
       System.out.println("Server stopped!");
   }

    public Future<String> reserve(List<Integer> list, int id_spectacol) {
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() {
                if(isRunning)
                    return service.reserve(list, id_spectacol, sala);
                else
                    return "TERMINATED";
            }
        });
        return future;
    }
}
