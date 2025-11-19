package cab.kh.com.access_control_list.model;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import javax.persistence.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "FEE_TYPE",
        visible = true
)
// Maps the discriminator value ("TIERED", "PERCENTAGE", etc.) to the concrete Java class.
@JsonSubTypes({
        @JsonSubTypes.Type(value = TieredFee.class, name = "TIERED"),
//         You'll need to define these when you add them, but they are included here for compilation:
        @JsonSubTypes.Type(value = PercentageFee.class, name = "PERCENTAGE"),
        @JsonSubTypes.Type(value = PerTransactionFee.class, name = "PER_TRANSACTION")
})

@Entity
// Single Table Strategy: All subclasses are mapped to the same table.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FEE_TYPE", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class FeeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feeName;
    private Boolean isActive = true;

    // Abstract method that must be implemented by all concrete fee types
    public abstract double calculateFee(double transactionAmount);

    // Helper method to round fees to 2 decimal places
    protected double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}