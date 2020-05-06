//import java.util.LinkedList;
//import java.util.List;

public class Customer{

        // DATI DEL CUSTOMER

    private final String name;
    private final String surname;
    private String address;
    private String cap;
    private String city;
    private String telephone;
    private String mail;
    private String password;
    //private final LoyaltyCard card;
    private String payment;
    //private Payment payment;
    //private List<Product> cartHistory;
    //private List<Product> cart;

        // COSTRUTTORE CUSTOMER

    public Customer(String name, String surname, String address, String cap, String city, String telephone, String mail, String password, String payment){

        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cap = cap;
        this.city = city;
        this.telephone = telephone;
        this.mail = mail;
        this.password = password;
        //this.card = new LoyaltyCard();
        this.payment = payment;
        //this.cartHistory = new LinkedList<Product>();
        //this.cart = new LinkedList<Product>();

    }

    //private void saveToDB(){
        
    //}

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

    /*public LoyaltyCard getCard(){

        return this.card;
    }*/

    /*public Payment getPayment(){

        return this.payment;
    }*/

    // getHistory e getCart


    public String toString(){

        return "Nome: "+this.name+"        Cognome: "+this.surname+"\nEmail: "+this.mail+"\nIndirizzo: "+this.address+"\nCittà:"+this.city+"          CAP: "+this.cap+"\nTelefono: "+this.telephone; 
    }
}