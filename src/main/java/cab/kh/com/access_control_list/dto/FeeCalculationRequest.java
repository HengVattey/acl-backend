package cab.kh.com.access_control_list.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class FeeCalculationRequest {


    @Positive(message = "Transaction amount must be positive.")
    private double transactionAmount;

    // A list of the IDs of the fees to apply (selected by the Angular UI)
    @NotNull(message = "Fee IDs list cannot be null.")
    private List<Long> feeIds;

    private String currency = "USD";
}
