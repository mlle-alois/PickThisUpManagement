package Services;

import Models.Status;
import Models.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

public class StatusService {

    private final DatabaseService databaseService;

    private static final String getStatus = "status";


    public StatusService(UserService user) {
        this.databaseService = new DatabaseService(user);
    }


    public Status[] getStatus(Body body) throws JsonProcessingException {
        HttpResponse<String> result = databaseService.GetRequest(body, getStatus);
        if (result.statusCode() < 300) {
            return body.objectMapper.readValue(result.body(), Status[].class);
        }
        return new Status[0];
    }


}
