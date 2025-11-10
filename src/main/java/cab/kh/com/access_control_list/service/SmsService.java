package cab.kh.com.access_control_list.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class SmsService {
    private final RestTemplate restTemplate;
    @Value("${my.service.url}")
    private  String  urlSms;
    String respone=" ";
    public SmsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String sendSms(String username, String pass,  String sender,  String smstext,  String
            gsm,  String intt,  String cd){
        //Create headers for to POST request
        HttpHeaders headers=new HttpHeaders();
        //I want to send with body x-www-form-urlencoded
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
        params.add("username",username);
        params.add("pass",pass);
        params.add("sender",sender);
        params.add("smstext", smstext);
        params.add("gsm",gsm);
        params.add("intt",intt);
        params.add("cd", UriUtils.encode(cd, StandardCharsets.UTF_8));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        try{
            respone= restTemplate.postForObject(urlSms, requestEntity, String.class);
        }catch (Exception exception){
            System.out.println(exception.fillInStackTrace());
        }
        return  respone;
    }
}

