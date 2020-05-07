import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Supermarket {

    private Customer currentCustomer;
    private Connection connection;
    private Statement st;
    private ResultSet rs;

        public Supermarket() throws SQLException {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC", "root", "admin");
            st = connection.createStatement();
            
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

            
            //delete();
            key.close();
        }

        public static void main(String[] args) {            

            try {
                new Supermarket();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private int getIDFromDB() throws SQLException{

            rs=st.executeQuery("SELECT code FROM loyaltyCard");            
            int id = 0;

            while(rs.next()){

                id = rs.getInt(1);
            }

            return id;
        }

        private void login() throws SQLException {

            String email, password;

            Scanner key = new Scanner(System.in);

            System.out.println("Inserire username: ");
            email = key.nextLine();

            System.out.println("Inserire Password: ");                
            password = key.nextLine();

            key.close();
            
            Statement st = connection.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM customer");
S        while(rs.next()){
                if(rs.getString("email").equals(email)){

                    if(rs.getString("password").equals(password)){

                        System.out.println("\n\nLogin effettuato!\n\n");                        
                        currentCustomer = 
                            new Customer(rs.getString("name"), rs.getString("surname"), rs.getString("address"), rs.getString("cap"), rs.getString("city"), 
                            rs.getString("telephone"), rs.getString("email"), rs.getString("password"), retrieveCardFromDB(rs.getInt("loyaltyCard")), rs.getString("payment"));
                            System.out.println(currentCustomer.toString());
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

        private LoyaltyCard retrieveCardFromDB(int code) throws SQLException {

            String sql = "SELECT * FROM loyaltyCard WHERE code = \""+code+"\"";
            rs=st.executeQuery(sql);
            rs.next();
            
            return new LoyaltyCard(rs.getInt("code"), rs.getTimestamp("emissionDate"));
        }

        private void delete() throws SQLException {

            System.out.println("\nELIMINAZIONE IN CORSO...\n");
            String sql1 = "DELETE FROM customer WHERE email = \""+currentCustomer.getMail()+"\"";
            System.out.println("Elimnazione Card n. "+currentCustomer.getCard().getCode());
            String sql2 = "DELETE FROM loyaltyCard WHERE code = \""+currentCustomer.getCard().getCode()+"\"";
            String sql3 = "DELETE FROM shopping WHERE customer = \""+currentCustomer.getMail()+"\"";
            Statement st = connection.createStatement();
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);
            st.executeUpdate(sql3);

            System.out.println("Utente rimosso con succeso!");
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

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);
            int idCard = getIDFromDB();

            st.executeUpdate("INSERT INTO loyaltyCard (code, emissionDate, points) VALUES ('"+idCard+"', '"+currentTime+"', '"+0+"')");

            st.executeUpdate("INSERT INTO Customer (name, surname, address, city, cap, telephone, email, password, payment, loyaltyCard) "
                            +"VALUES ('"+name+"', '"+surname+"', '"+address+"', '"+city+"', '"+cap+"', '"+telephone+"','"+email+"', '"+password+"', '"+payment+"', "+idCard+")");             

            System.out.println("Crezione Account cliente conlcusa con successo!");

            currentCustomer = new Customer(name, surname, address, cap, city, telephone, email, password, new LoyaltyCard(getIDFromDB(), dt), payment);

        }

}


