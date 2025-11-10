package cab.kh.com.access_control_list.controller;

import cab.kh.com.access_control_list.service.SmsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class SmsController {
    private final SmsService smsService;
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }
    @PostMapping("/sms-proxy")
    public  String sendSmsTo(@RequestParam("username") String username, @RequestParam("pass") String pass,
                             @RequestParam("sender") String sender, @RequestParam("smstext") String smstext,
                             @RequestParam("gsm") String gsm, @RequestParam("intt") String intt,
                             @RequestParam("cd") String cd){
        return smsService.sendSms(username,pass, sender,smstext,gsm,intt,cd);
    }
}
