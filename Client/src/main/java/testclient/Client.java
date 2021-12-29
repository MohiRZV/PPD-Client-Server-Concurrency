package testclient;

import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Client extends Thread{

    String URL = "http://localhost:8080/ppd";
    RestTemplate template = new RestTemplate();

    private <T> T execute(Callable<T> callable) throws Exception {
        return callable.call();
    }

    public String reserve(List<Integer> places, int id_spectacol) throws Exception {
        Map map = new HashMap();
        map.put("seats",places);
        map.put("id_spectacol", id_spectacol);
        return execute(() -> template.postForObject(URL+"/reserve", map, String.class));
    }

    private Random rnd = new Random();
    private int generateRandom(int max) {
        return rnd.nextInt(max)+1;
    }

    private List<Integer> randomPlaces(int max,int bound) {
        List<Integer> list = new ArrayList<>();

        int nr = rnd.nextInt(max)+1;
        for(int i=0;i<nr;i++) {
            int place = generateRandom(bound);
            while(list.contains(place))
                place = generateRandom(bound);
            list.add(place);
        }

        return list;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        long lifeSpan = TimeUnit.NANOSECONDS.convert(1, TimeUnit.MINUTES);
        long sleepTime = TimeUnit.MILLISECONDS.convert(2,TimeUnit.SECONDS);

        while(System.nanoTime()-startTime<lifeSpan) {
            List<Integer> places = randomPlaces(5,100);
            System.out.println(places);

            int spectacol = generateRandom(3);
            try {
                String result = reserve(places, spectacol);
                if(result.equals("TERMINATED"))
                    this.interrupt();
                System.out.println(result);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
