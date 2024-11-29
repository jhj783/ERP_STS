package com.erp.erpsystem.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.erp.erpsystem.db.Count;
import com.erp.erpsystem.db.CountRepository;

@Service
public class SendMoneyApiService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CountRepository countRepository;
	
	@Autowired
	private RefreshAccountFromApiService refreshAccountFromApiService;
	
    @Value("${api.access.token}")
    private String accessToken;
	
	public void sendMoneyApi(String description, String value) {
		
	    String url = "https://developers.nonghyup.com/DrawingTransfer.nh";
		String finAcno1 = "00820100026740000000000018326"; // 모계좌에서 출금
		//String finAcno2 = "00820100026740000000000018344"; // 모계좌에 입금
		
	    // 헤더 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
	    
	    // Istuno
	    Long id = 1L;
	    Optional<Count> countOpt = countRepository.findById(id);
	    String istuno = String.valueOf(countOpt.get().getIstuno());
	    countRepository.incrementIstuno();
	    
        // 오늘 날짜
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);
	    
	    // 요청 바디 생성
	    String requestBody = """
	    {
	      "Header": {
	        "ApiNm": "DrawingTransfer",
	        "Tsymd": "%s",
	        "Trtm": "112428",
	        "Iscd": "002674",
	        "FintechApsno": "001",
	        "ApiSvcCd": "DrawingTransferA",
	        "IsTuno": "%s",
	        "AccessToken": "%s"
	      },
	      "FinAcno": "%s",
	      "Tram": "%s",
	      "DractOtlt": "%s"
	    }
	    """.formatted(formattedDate, istuno, accessToken, finAcno1, value, description);
	
	    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
	
	    // POST 요청 수행
	    restTemplate.postForEntity(url, entity, String.class);
	
	    // 저장
	    refreshAccountFromApiService.saveAccountsFromApi();

	}
}
