
import java.time.LocalDate;

public class LoyaltyCard {

    private static final int code = 0;
    private final LocalDate emissionDate;
    private int points;


    public LoyaltyCard(){
        LoyaltyCard.code++;
        LoyaltyCard.emissionDate = LocalDate.now();
        this.points = 0;
    }

}
