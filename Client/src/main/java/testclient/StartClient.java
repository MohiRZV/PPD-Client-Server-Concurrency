package testclient;

public class StartClient {
    public static void main(String[] args) throws Exception {
        int nrClients = 10;
        for(int i=0;i<nrClients;i++)
            new Client().start();
    }
}
