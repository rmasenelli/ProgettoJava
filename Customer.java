import java.util.LinkedList;
import java.util.List;

public class Customer{

    private static final String name;
    private static final String surname;
    private static final String address;
    private static final String cap;
    private static final String city;
    private static final String telepohne;
    private static final String mail;
    private static final String password;
    private static final LoyaltyCard card;
    private static final Payment payment;
    private static final List<Product> cartHistory;
    private static final List<Product> cart;

    public Customer(String name, String surname, String address, String cap, String city, 
            String telephone, String mail, String password, LoyaltyCard card, Pyment payment){

        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cap = cap;
        this.city = city;
        this.telephone = telephone;
        this.mail = mail;
        this.password = password;
        this.payment = payment;
        this.cartHistory = new LinkedList<Product>();
        this.cart = new LinkedList<Product>();


    }
}