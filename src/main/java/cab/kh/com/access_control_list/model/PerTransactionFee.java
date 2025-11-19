package cab.kh.com.access_control_list.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("PER_TRANSACTION")
@EqualsAndHashCode(callSuper = true)
@Data
public class PerTransactionFee extends FeeConfig {

    private Double fixedAmount;

    private String currency = "USD";

    @Override
    public double calculateFee(double transactionAmount) {
        if (fixedAmount == null || fixedAmount < 0) {
            return 0.0;
        }
        return round(fixedAmount);
    }
}