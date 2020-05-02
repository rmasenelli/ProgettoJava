import java.util.LinkedList;
import java.util.List;

public class Customer{

        // DATI DEL CUSTOMER

    private final String name;
    private final String surname;
    private final String address;
    private final String cap;
    private final String city;
    private final String telephone;
    private final String mail;
    private final String password;
    private final LoyaltyCard card;
    private final Payment payment;
    private final List<Product> cartHistory;
    private final List<Product> cart;

        // COSTRUTTORE CUSTOMER

    public Customer(String name, String surname, String address, String cap, String city, 
            String telephone, String mail, String password, LoyaltyCard card, Payment payment){

        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cap = cap;
        this.city = city;
        this.telephone = telephone;
        this.mail = mail;
        this.password = password;
        this.card = new LoyaltyCard();
        this.payment = payment;
        this.cartHistory = new LinkedList<Product>();
        this.cart = new LinkedList<Product>();

    }

        // GETTERS CUSTOMER

    public String getName(){
        
        return this.name;
    }

    public String getSurname(){
        
        return this.surname;
    }

    public String getAddress(){
        
        return this.address;
    }

    public String getCap(){

        return this.cap;
    }

    public String getCity(){

        return this.city;
    }

    public String getTelephone(){

        return this.telephone;
    }

    public String getMail(){

        return this.mail;
    }

    //getPassword? :(

    public LoyaltyCard getCard(){

        return this.card;
    }

    public Payment getPayment(){

        return this.payment;
    }

    // getHistory e getCart
}