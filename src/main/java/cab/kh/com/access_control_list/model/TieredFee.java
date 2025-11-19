package cab.kh.com.access_control_list.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("TIERED")
@EqualsAndHashCode(callSuper = true)
@Data
public class TieredFee extends FeeConfig {

    @ElementCollection
    @CollectionTable(name = "FEE_TIERS", joinColumns = @JoinColumn(name = "fee_config_id"))
    private List<FeeTier> tiers;

    @Override
    public double calculateFee(double transactionAmount) {
        if (tiers == null || tiers.isEmpty()) {
            return 0.0;
        }

        for (FeeTier tier : tiers) {
            // Check if the transaction amount falls within the tier range
            boolean isAboveMin = transactionAmount >= tier.getMinAmount();
            boolean isBelowMax = tier.getMaxAmount() == null || transactionAmount <= tier.getMaxAmount();

            if (isAboveMin && isBelowMax) {
                double calculatedFee = 0.0;
                if ("FIXED".equalsIgnoreCase(tier.getChargeType())) {
                    calculatedFee = tier.getChargeValue();
                } else if ("PERCENTAGE".equalsIgnoreCase(tier.getChargeType())) {
                    calculatedFee = transactionAmount * (tier.getChargeValue() / 100.0);
                }
                return round(calculatedFee);
            }
        }
        return 0.0; // No matching tier found
    }

    @Embeddable
    @Data
    public static class FeeTier {
        private Double minAmount;
        private Double maxAmount; // Can be null for "and up"
        private String chargeType; // FIXED or PERCENTAGE
        private Double chargeValue;
    }
}