import java.io.IOException;

public class PostRequest {

    public static void main(String[] args) throws IOException {


        User user = new User();

        var testBody = new Body();
        testBody.addValueToBody("mail","IchaiDev");
        testBody.addValueToBody("password","non");

        user.Login(testBody);
        Board[] boards =  user.getBoard(testBody);
    }
}