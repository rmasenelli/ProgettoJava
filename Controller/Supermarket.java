import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Supermarket {

    private Customer currentCustomer;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private static final String connectionString = "jdbc:mysql://localhost:3306/supermarketdb?serverTimezone=UTC";

    public Supermarket() {

        try {

            connection = DriverManager.getConnection(connectionString, "root", "admin");
            statement = connection.createStatement();

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
                    delete();

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

            resultSet=statement.executeQuery("SELECT MAX(code) FROM loyaltyCard");            

            resultSet.next();
            /*int id = 0;

            while(resultSet.next()){

                id = resultSet.getInt(1);
                //System.out.println("ID scorso: "+id);
            }*/
            return resultSet.getInt(1)+1;//id+1;
        }

        private void login() throws SQLException {

            String email, password;

            Scanner key = new Scanner(System.in);

            System.out.print("\nUsername: ");
            email = key.nextLine();

            System.out.print("Password: ");                
            password = key.nextLine();

            key.close();
            
            try {
                resultSet=statement.executeQuery("SELECT * FROM customer WHERE email = \""+email+"\"");
                resultSet.next();

                if(resultSet.getString("password").equals(encrypt(password))){

                    System.out.println("\nLOGIN EFFETTUATO!\n");                        

                    currentCustomer = 
                        new Customer(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("address"), 
                        resultSet.getString("cap"), resultSet.getString("city"), resultSet.getString("telephone"), resultSet.getString("email"), 
                        resultSet.getString("password"), retrieveCardFromDB(resultSet.getInt("loyaltyCard")), Payment.valueOf(resultSet.getString("payment")));

                    System.out.println(currentCustomer.toString());
                    return;
                }
                else{
                    System.out.println("\n\nPassword Errata!\n\n");
                    return;
                }
            } catch (Exception e) {
                System.out.println("\nUtente inesistente!");
                e.printStackTrace();
            }
        }

        private LoyaltyCard retrieveCardFromDB(int code) throws SQLException {

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM loyaltyCard WHERE code = \""+code+"\"";
            ResultSet resultSet=statement.executeQuery(sql);
            resultSet.next();
            
            return new LoyaltyCard(resultSet.getInt("code"), resultSet.getTimestamp("emissionDate"));
        }

        private void delete() throws SQLException {

            System.out.println("\nELIMINAZIONE IN CORSO...\n");
            
            String sql1 = "DELETE FROM customer WHERE email = \""+currentCustomer.getMail()+"\"";
            String sql2 = "DELETE FROM loyaltyCard WHERE code = \""+currentCustomer.getCard().getCode()+"\"";
            String sql3 = "DELETE FROM shopping WHERE customer = \""+currentCustomer.getMail()+"\"";
            
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
            statement.executeUpdate(sql3);

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

            password=encrypt(password);

            key.close();

            //Statement statement = connection.createStatement();

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);
            
            int idCard = getIDFromDB();

           // System.out.println("ID Retrieved:"+idCard);

            statement.executeUpdate("INSERT INTO loyaltyCard (code, emissionDate, points) VALUES ('"+idCard+"', '"+currentTime+"', '"+0+"')");

            statement.executeUpdate("INSERT INTO Customer (name, surname, address, city, cap, telephone, email, password, payment, loyaltyCard) "
                            +"VALUES ('"+name+"', '"+surname+"', '"+address+"', '"+city+"', '"+cap+"', '"+telephone+"','"+email+"', '"+password+"', '"+payment+"', "+idCard+")");             

            System.out.println("\nCrezione Account cliente conclusa con successo!");

            currentCustomer = new Customer(name, surname, address, cap, city, telephone, email, password, new LoyaltyCard(getIDFromDB(), dt), payment);

        }

        private static String encrypt(String input){

            try { 
                // getInstance() method is called with algorithm SHA-1 
                MessageDigest md = MessageDigest.getInstance("SHA-1"); 
    
                // digest() method is called 
                // to calculate message digest of the input string 
                // returned as array of byte 
                byte[] messageDigest = md.digest(input.getBytes()); 
    
                // Convert byte array into signum representation 
                BigInteger no = new BigInteger(1, messageDigest); 
    
                // Convert message digest into hex value 
                String hashtext = no.toString(16); 
    
                // Add preceding 0s to make it 32 bit 
                while (hashtext.length() < 32) { 
                    hashtext = "0" + hashtext; 
                } 
    
                // return the HashText 
                return hashtext; 
            } 
    
            // For specifying wrong message digest algorithms 
            catch (NoSuchAlgorithmException e) { 
                throw new RuntimeException(e); 
            } 
        }
}


