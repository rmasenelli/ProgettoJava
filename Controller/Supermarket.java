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

    public Supermarket() {

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC", "root", "admin");
            st = connection.createStatement();

        } catch (SQLException e) {

            System.err.println("\nErrore di connessione!\n");
            e.printStackTrace();
        }

        System.out.println("\nConnessione al DB riuscita\n");
        

        Scanner key = new Scanner(System.in);
        int choose;

        System.out.print("\nOpzioni:\n\n1. Login;\n2. Sign-Up\n\nScelta: ");

            choose = key.nextInt();

            if(choose == 1){
                try{
                    login();

                }
                catch(SQLException e){
                    System.err.println("\nErrore di login!\n");
                    e.printStackTrace();
                }
            }
            else if(choose == 2){
                
                try{
                    signup();

                }
                catch(SQLException e){
                    System.err.println("\nErrore di signup!\n");
                    e.printStackTrace();
                }

            }

            //delete();
            key.close();

            System.out.println("\n");
        }

        public static void main(String[] args) {            

            
                new Supermarket();
        }

        private int getIDFromDB() throws SQLException{

            rs=st.executeQuery("SELECT code FROM loyaltyCard");            
            int id = 0;

            while(rs.next()){

                id = rs.getInt(1);
                //System.out.println("ID scorso: "+id);
            }
            return id+1;
        }

        private void login() throws SQLException {

            String email, password;

            Scanner key = new Scanner(System.in);

            System.out.print("\nusername: ");
            email = key.nextLine();

            System.out.print("Password: ");                
            password = key.nextLine();

            key.close();
            
            Statement st = connection.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM customer");

        while(rs.next()){
                if(rs.getString("email").equals(email)){

                    if(rs.getString("password").equals(password)){

                        System.out.println("\nLOGIN EFFETTUATO!\n");                        
                        currentCustomer = 
                            new Customer(rs.getString("name"), rs.getString("surname"), rs.getString("address"), rs.getString("cap"), rs.getString("city"), 
                            rs.getString("telephone"), rs.getString("email"), rs.getString("password"), retrieveCardFromDB(rs.getInt("loyaltyCard")), Payment.valueOf(rs.getString("payment")));
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
            //System.out.println("Elimnazione Card n. "+currentCustomer.getCard().getCode());
            String sql2 = "DELETE FROM loyaltyCard WHERE code = \""+currentCustomer.getCard().getCode()+"\"";
            String sql3 = "DELETE FROM shopping WHERE customer = \""+currentCustomer.getMail()+"\"";
            
            Statement st = connection.createStatement();
            
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);
            st.executeUpdate(sql3);

            currentCustomer= null;

            System.out.println("Utente rimosso con successo!");
        }

        private void signup() throws SQLException {

            String email, name, surname, password, address, city, cap, telephone;
            Payment payment;
            Scanner key = new Scanner(System.in);
                
            System.out.print("Inserire email: ");
            email = key.nextLine();
            System.out.print("Inserire nome: ");
            name = key.nextLine();
            System.out.print("Inserire cognome: ");
            surname = key.nextLine();
            System.out.print("Inserire password: ");
            password = key.nextLine();
            System.out.print("Inserire indirizzo: ");
            address = key.nextLine();
            System.out.print("Inserire città: ");
            city = key.nextLine();
            System.out.print("Inserire cap: ");
            cap = key.nextLine();
            System.out.print("Inserire telefono: ");
            telephone = key.nextLine();
            System.out.print("\nScegliere il pagamento preferito:\n\n1. PayPal\n2. Carta di Credito\n3. Alla consegna\n\nScelta: ");

            payment = Payment.values()[key.nextInt()-1]; 
            
            key.close();

            //Statement st = connection.createStatement();

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);
            
            int idCard = getIDFromDB();

           // System.out.println("ID Retrieved:"+idCard);

            st.executeUpdate("INSERT INTO loyaltyCard (code, emissionDate, points) VALUES ('"+idCard+"', '"+currentTime+"', '"+0+"')");

            st.executeUpdate("INSERT INTO Customer (name, surname, address, city, cap, telephone, email, password, payment, loyaltyCard) "
                            +"VALUES ('"+name+"', '"+surname+"', '"+address+"', '"+city+"', '"+cap+"', '"+telephone+"','"+email+"', '"+password+"', '"+payment+"', "+idCard+")");             

            System.out.println("\nCrezione Account cliente conlcusa con successo!");

            currentCustomer = new Customer(name, surname, address, cap, city, telephone, email, password, new LoyaltyCard(getIDFromDB(), dt), payment);

        }
}


