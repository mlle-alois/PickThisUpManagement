package Services;

import java.net.http.HttpClient;

public class Client {
    private static HttpClient client = null;

    private Client(){
    }

    public static synchronized HttpClient getInstance() {
        if (client == null){
            client = HttpClient.newHttpClient();
        }
       return client;
    }



}
