package cab.kh.com.access_control_list.controller;
import cab.kh.com.access_control_list.model.Fee;
import cab.kh.com.access_control_list.service.FeeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fee")
public class FeeController {

    private  final FeeService feeService;
    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

@PostMapping
public Fee createFee(@Valid @RequestBody Fee req){
    System.out.println( "FEE"  +req.toString());
        return feeService.createFee(req);
}

@GetMapping
    public List<Fee> getAll(){
        return feeService.getAllFeeName();
}

//@PutMapping("/{id}")
//    public Fee updateFee(@PathVariable Long id, @RequestBody Fee req) {
//
//    Fee fee=
//
//
//    }


}
