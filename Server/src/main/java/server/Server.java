package server;

import model.Sala;
import service.Service;

import java.util.List;
import java.util.concurrent.*;

public class Server{

    private Service service = Service.getInstance();
    private Sala sala = new Sala(100);


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

    public void run() {
       long startTime = System.nanoTime();

       sala.setSpectacole(service.getAllSpectacole());
       long lifeSpan = TimeUnit.NANOSECONDS.convert(1, TimeUnit.MINUTES);
       long sleepTime = TimeUnit.MILLISECONDS.convert(5,TimeUnit.SECONDS);

       while(System.nanoTime()-startTime<lifeSpan) {

           System.out.println(service.report(service.getAllVanzari()));
           System.out.println(service.report(sala.getVanzari()));

           try {
               Thread.sleep(sleepTime);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
   }

    public Future<String> reserve(List<Integer> list, int id_spectacol) {
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() {
                return service.reserve(list, id_spectacol, sala);
            }
        });
        return future;
    }
}
