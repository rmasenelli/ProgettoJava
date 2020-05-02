
import java.time.LocalDate;

public class LoyaltyCard {

    private static final int code;
    private static final LocalDate emissionDate;
    private static int points;


    public LoyaltyCard(){
        LoyaltyCard.code++;
        LoyaltyCard.emissionDate = LocalDate.now();
        this.points = 0;
    }

}
