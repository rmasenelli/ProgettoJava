import java.awt.*;

public class Product {

    private static int id = 0;
    private final int code;
    private final String name;
    private final String brand;
    private int quantity; // non final puo cambiare
    private double price;
    private final Image image; 

    public Product(String name, String brand, int quantity, double price){
        Product.id++;
        this.code = id;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
        this.image = setImage(); // dobbiamo fare che in base al nome poi si apre il file giusto automaticamente 
    }

    private Image setImage(){
        return null;
    }

    public Image getImage(){
        return this.image;
    }

    public void addQuantity(){
        this.quantity++;
    }

    public int getCode(){
        
        return this.code;
    }

    public String getName(){

        return this.name;
    }

    public String getBrand(){

        return this.brand;
    }

    public int getQuantity(){

        return this.quantity;
    }

    public double getPrice(){

        return this.price;
    }


}
