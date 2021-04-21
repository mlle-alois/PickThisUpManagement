import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class PostRequest {

    public static void main(String[] args) throws IOException, InterruptedException {

        var values = new HashMap<String, String>() {{
            put("mail", "IchaiAdmin");
            put ("password", "non");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

       // HttpClient client = HttpClient.newHttpClient();
        HttpClient client = Client.getInstance();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/auth/login")).setHeader("Authorization","Bearer $2b$05$z4qQ9SkKwPZrLPTrfp7mAuKgxE9HvAp0Vw6rbpl32IJexh.8uedT.")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        Map<String, Object> map
                = objectMapper.readValue(response.body(), new TypeReference<Map<String,Object>>(){});

        System.out.println(response.body());
    }
}