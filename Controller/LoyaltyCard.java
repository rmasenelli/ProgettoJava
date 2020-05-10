import java.util.Date;

public class LoyaltyCard {

    private final int code;
    private final Date emissionDate;
    private int points;


    public LoyaltyCard(int id, Date emissionDate){

        this.code = id;
        this.emissionDate = emissionDate;
        this.points = 0;
    }

    public int getCode(){
        
        return this.code;
    }

    public int getPoints(){

        return this.points;
    }

}
