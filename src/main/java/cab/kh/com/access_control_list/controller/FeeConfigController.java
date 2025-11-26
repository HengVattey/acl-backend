package cab.kh.com.access_control_list.controller;

import cab.kh.com.access_control_list.dto.FeeCalculationRequest;
import cab.kh.com.access_control_list.model.FeeConfig;
import cab.kh.com.access_control_list.service.FeeConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fees")
public class FeeConfigController {
    private final FeeConfigService service;

    public FeeConfigController(FeeConfigService service) {
        this.service = service;
    }



    @GetMapping
    public ResponseEntity<List<FeeConfig>> getAllFees() {
        return ResponseEntity.ok(service.getAllFeeConfigs());
    }


    @GetMapping("/{id}")
    public ResponseEntity<FeeConfig> getFeeById(@PathVariable Long id) {
        return service.getFeeConfigById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<FeeConfig> createFee(@Valid @RequestBody FeeConfig feeConfig) {
        FeeConfig savedFee = service.saveFeeConfig(feeConfig);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFee);
    }


    @PutMapping("/{id}")
    public ResponseEntity<FeeConfig> updateFee(@PathVariable Long id, @Valid @RequestBody FeeConfig feeConfig) {
        if (!service.getFeeConfigById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        feeConfig.setId(id); // Ensure the ID is set for update
        FeeConfig updatedFee = service.saveFeeConfig(feeConfig);
        return ResponseEntity.ok(updatedFee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFee(@PathVariable Long id) {
        if (!service.getFeeConfigById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteFeeConfig(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calculate")
    public ResponseEntity<Double> calculateAggregateFee(@Valid @RequestBody FeeCalculationRequest request) {
        double totalFee = service.calculateTotalFee(request);
        return ResponseEntity.ok(totalFee);
    }
}
