import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Supermarket {

    private Customer currentCustomer;
    private Connection connection;

        public Supermarket() throws SQLException {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC", "root", "admin");
           

            Scanner key = new Scanner(System.in);
            int choose;

            System.out.println("Inserire scelta:\n1. Login;\n2. Sign-Up;");
            choose = key.nextInt();

            if(choose == 1){
                login();

            }
            else if(choose == 2){

                signup();

            }

            /*System.out.println("Inserire scelta:\n1. Stampa Anagrafica;\n2. Eliminare Utente;\n3. Modificare Dati");
            choose = key.nextInt();

            switch(choose){
                case 1 : System.out.println(currentCustomer.toString());
                        break;
                case 2 : //deleteCustomer();
                        break;
                case 3 : //modifyCustomer();
                        break;
            }
            */
            key.close();
        }

        public static void main(String[] args) {            

            try {
                new Supermarket();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void login() throws SQLException {

            String email, password;
            Scanner key = new Scanner(System.in);

            System.out.println("Inserire username: ");
            email = key.nextLine();

            System.out.println("Inserire Password: ");                
            password = key.nextLine();

            
            Statement st = connection.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM Customer");
            while(rs.next()){
                if(rs.getString("email").equals(email)){
                    if(rs.getString("C_password").equals(password)){
                        System.out.println("\n\nLogin effettuato!\n\n");
                        currentCustomer = new Customer(rs.getString("C_name"), rs.getString("C_surname"), rs.getString("C_address"), rs.getString("C_cap"), rs.getString("C_city"), rs.getString("C_telephone"), rs.getString("email"), rs.getString("C_password"), rs.getString("C_payment"));
                        return;
                    }
                    else{
                        System.out.println("\n\nPassword Errata!\n\n");
                        return;
                    }
                }   
            }

            System.out.println("\nUtente inesistente!");

        }

        private void signup() throws SQLException {

            String email, name, surname, password, address, city, cap, telephone, payment;
            Scanner key = new Scanner(System.in);
                
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

            key.close();

            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO Customer (email, C_name, C_surname, C_password, C_address, C_city, C_CAP, C_telephone, C_payment) "
                            +"VALUES ('"+email+"', '"+name+"', '"+surname+"', '"+password+"', '"+address+"', '"+city+"', '"+cap+"', '"+telephone+"', '"+payment+"')");             

            System.out.println("Crezione Account cliente conlcusa con successo!");

            currentCustomer = new Customer(name, surname, address, cap, city, telephone, email, password, payment);

            

        }

}


