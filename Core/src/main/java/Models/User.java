package Models;
 class ProfilePicture {
    public Integer mediaId;
    public String mediaPath;
}
class Type {
     public Integer userTypeId;
     public String userTypeLibelle;
}

public class User {
    public String mail;
    public String password;
    public String name;
    public String firstname;
    public String phoneNumber;
    public Integer profilePictureId;
    public ProfilePicture profilePicture;
    public Integer typeId;
    public Type type;
}
