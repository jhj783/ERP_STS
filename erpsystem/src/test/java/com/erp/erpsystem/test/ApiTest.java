package com.erp.erpsystem.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.erp.erpsystem.db.CountRepository;
import com.erp.erpsystem.db.Count;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ApiTest {

	@TestConfiguration
	static class TestConfig {
		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CountRepository countRepository;

	@Test
    public void testDrawingTransfer() {
		String finAcno1 = "00820100026740000000000018326"; //출금
		String finAcno2 = "00820100026740000000000018344"; //입금
		
		List<Object[]> expenses = List.of(
				new Object[] {"복리후생비", "650000"},
			    new Object[] {"원자재구입", "8100000"},
			    new Object[] {"물류비", "520000"},
			    new Object[] {"공과금", "1300000"},
			    new Object[] {"임대료", "2600000"},
			    new Object[] {"월급 (김랜서)", "3900000"},
			    new Object[] {"월급 (윤아처)", "3900000"},
			    new Object[] {"박세이버", "3900000"},
			    new Object[] {"관리비", "195000"},
			    new Object[] {"원자재구입", "8100000"},
			    new Object[] {"소모품비", "390000"},
			    new Object[] {"원자재구입", "8100000"},
			    new Object[] {"원자재구입", "8100000"},
			    new Object[] {"원자재구입", "8100000"}
			);

			List<Object[]> incomes = List.of(
			    new Object[] {"대금 입금 (기업1)", "8110000"},
			    new Object[] {"대금 입금 (기업2)", "8800000"},
			    new Object[] {"대금 입금 (기업4)", "9200000"},
			    new Object[] {"대금 입금 (기업3)", "7960000"}
			);

        
        // 지출 트랜잭션 처리
        for (Object[] expense : expenses) {
            createDummyApiData((String) expense[0], (String) expense[1], finAcno1);
        }
        
        // 수입 트랜잭션 처리
        for (Object[] income : incomes) {
            createDummyApiData((String) income[0], (String) income[1], finAcno2);
        }
		

        
    }
	
	public void createDummyApiData(String description, String value, String finAcno) {
	    String url = "https://developers.nonghyup.com/DrawingTransfer.nh";
		
	    // 헤더 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
	    
	    //
	    Long id = 1L;
	    Optional<Count> countOpt = countRepository.findById(id);
	    String istuno = String.valueOf(countOpt.get().getIstuno());
	    countRepository.incrementIstuno();
	    
	    // 요청 바디 생성
	    String requestBody = """
	    {
	      "Header": {
	        "ApiNm": "DrawingTransfer",
	        "Tsymd": "20241127",
	        "Trtm": "112428",
	        "Iscd": "002674",
	        "FintechApsno": "001",
	        "ApiSvcCd": "DrawingTransferA",
	        "IsTuno": "%s",
	        "AccessToken": "a7ae51f122fe3639c8d4f890a2ec7e702eba6eeda9869c779f6463e54cf589b9"
	      },
	      "FinAcno": "%s",
	      "Tram": "%s",
	      "DractOtlt": "%s"
	    }
	    """.formatted(istuno, finAcno, value, description);
	
	    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
	
	    // POST 요청 수행
	    ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
	
	    // 응답 상태 코드 검증
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	
	    // 응답 본문 검증
	    String responseBody = response.getBody();
	    assertNotNull(responseBody);
	    assertTrue(responseBody.contains("\"Rsms\":\"정상처리 되었습니다.\""));
	}
}
