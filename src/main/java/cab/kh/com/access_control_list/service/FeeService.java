package cab.kh.com.access_control_list.service;
import cab.kh.com.access_control_list.dto.FeeReq;
import cab.kh.com.access_control_list.model.Fee;
import cab.kh.com.access_control_list.repository.FeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeService {
    private  final FeeRepository feeRepository;

    public  FeeService(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

   public Fee createFee(Fee req) {
       return   feeRepository.save(req);
   }

   public List<Fee> getAllFeeName(){
        return feeRepository.findAll();
   }


}
