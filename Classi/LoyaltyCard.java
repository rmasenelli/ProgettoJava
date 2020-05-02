
import java.time.LocalDate;

public class LoyaltyCard {

    private static int id = 0;
    private final int code;
    private final LocalDate emissionDate;
    private int points;


    public LoyaltyCard(){
        LoyaltyCard.id++;
        this.code = id;
        this.emissionDate = LocalDate.now();
        this.points = 0;
    }

}
