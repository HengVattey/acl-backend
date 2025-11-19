package cab.kh.com.access_control_list.service;

import cab.kh.com.access_control_list.dto.FeeCalculationRequest;
import cab.kh.com.access_control_list.model.FeeConfig;
import cab.kh.com.access_control_list.repository.FeeConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeeConfigService {

    private final FeeConfigRepository repository;
    public FeeConfigService(FeeConfigRepository repository) {
        this.repository = repository;
    }

    public List<FeeConfig> getAllFeeConfigs() {
        return repository.findAll();
    }


    public Optional<FeeConfig> getFeeConfigById(Long id) {
        return repository.findById(id);
    }

    public FeeConfig saveFeeConfig(FeeConfig feeConfig) {
        return repository.save(feeConfig);
    }

    public void deleteFeeConfig(Long id) {
        repository.deleteById(id);
    }


    public double calculateTotalFee(FeeCalculationRequest request) {
        double totalFee = 0.0;
        double amount = request.getTransactionAmount();
        List<Long> feeIds = request.getFeeIds();

        List<FeeConfig> configs = repository.findAllById(feeIds);

        for (FeeConfig config : configs) {
            if (config.getIsActive() != null && config.getIsActive()) {
                double fee = config.calculateFee(amount);
                totalFee += fee;
            }
        }

        return totalFee;
    }
}