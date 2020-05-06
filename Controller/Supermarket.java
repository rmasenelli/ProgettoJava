import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Supermarket {

    private Customer currentCustomer;

        public static void main(String[] args) {            

            new Supermarket();      

        }

        public Supermarket(){

            String email, name, surname, password, address, city, cap, telephone, payment;

            Scanner key = new Scanner(System.in);

            System.out.println("Inseire scelta:\n1. Login;\n2. Sign-Up;");
            String choose = key.nextLine();

            if(choose.equals("1")){

                System.out.println("Inserire username: ");
                email = key.nextLine();
                System.out.println("Inserire Password: ");
                password = key.nextLine();
                login(email, password);

            }
            else if(choose.equals("2")){

                System.out.println("Inserire email: ");
                email = key.nextLine();
                System.out.println("Inserire nome: ");
                name = key.nextLine();
                System.out.println("Inserire cognome: ");
                surname = key.nextLine();
                System.out.println("Inserire password: ");
                password = key.nextLine();
                System.out.println("Inserire indirizzo: ");
                address = key.nextLine();
                System.out.println("Inserire città: ");
                city = key.nextLine();
                System.out.println("Inserire cap: ");
                cap = key.nextLine();
                System.out.println("Inserire telefono: ");
                telephone = key.nextLine();
                System.out.println("Inserire pagamento: ");
                payment = key.nextLine();

                signup(email, name, surname, password, address, city, cap, telephone, payment);

            }

            key.close();
        }

        private void login(String email, String password){

            try{
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC", "root", "admin");
                Statement st = conn.createStatement();
                ResultSet rs=st.executeQuery("SELECT C_password FROM Customer WHERE email='"+email+"'");
                rs.next();
                if(rs.getString("C_password").equals(password)){

                    System.out.println("\nLogin effettuato!");
                    currentCustomer = new Customer(rs.getString("C_name"), rs.getString("C_surname"), rs.getString("C_address"), rs.getString("C_cap"), rs.getString("C_city"), rs.getString("C_telephone"), rs.getString("email"), rs.getString("C_password"), rs.getString("C_payment"));
                }                  
                else
                    System.out.println("\nErrore!");
                conn.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }

        }

        private void signup(String email, String name, String surname, String password, String address, String city, String cap, String telephone, String payment){

            try{
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC", "root", "admin");
                Statement st = conn.createStatement();
                st.executeUpdate("INSERT INTO Customer (email, C_name, C_surname, C_password, C_address, C_city, C_CAP, C_telephone, C_payment) "
                +"VALUES ('"+email+"', '"+name+"', '"+surname+"', '"+password+"', '"+address+"', '"+city+"', '"+cap+"', '"+telephone+"', '"+payment+"')");
                System.out.println("Crezione Account cliente conlcusa con successo!");
                conn.close();
                currentCustomer = new Customer(name, surname, address, cap, city, telephone, email, password, payment);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }

        }

    }


