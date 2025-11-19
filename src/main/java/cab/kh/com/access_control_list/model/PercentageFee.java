package cab.kh.com.access_control_list.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("PERCENTAGE")
@EqualsAndHashCode(callSuper = true)
@Data
public class PercentageFee extends FeeConfig {

    private Double percentageRate;

    private Double capAmount;

    @Override
    public double calculateFee(double transactionAmount) {
        if (percentageRate == null || percentageRate <= 0) {
            return 0.0;
        }

        double calculatedFee = transactionAmount * (percentageRate / 100.0);

        if (capAmount != null && capAmount > 0 && calculatedFee > capAmount) {
            calculatedFee = capAmount;
        }

        return round(calculatedFee);
    }
}