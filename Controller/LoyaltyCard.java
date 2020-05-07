
//import java.time.LocalDate;

public class LoyaltyCard {

    private final int code;
    private final java.util.Date emissionDate;
    private int points;


    public LoyaltyCard(int id, java.util.Date emissionDate){

        this.code = id;
        this.emissionDate = emissionDate;
        this.points = 0;
    }

    public int getCode(){
        return this.code;
    }

}
