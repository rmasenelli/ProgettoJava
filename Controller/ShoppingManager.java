
public class ShoppingManager {

    //private static int serialNumber = 0;
    private final String name;
    private final String surname;
    private String role;
    private final String login;
   // private String password;

    public ShoppingManager(String name, String surname, String role, String login, String password){

        //ShoppingManager.serialNumber++;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.login = login;
        //this.password = password;
    }

    public String getName(){

        return this.name;
    }

    public String getSurname(){

        return this.surname;
    }

    public String getRole(){

        return this.role;
    }

    public String getLogin(){

        return this.login;
    }

    //getPassword
}