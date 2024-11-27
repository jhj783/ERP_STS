package com.erp.erpsystem.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.erp.erpsystem.db.Count;
import com.erp.erpsystem.db.CountRepository;



@Service
public class ReceiveApiService {

    @Autowired
    private CountRepository countRepository;

    @Autowired
    private RestTemplate restTemplate;

    public JSONArray searchApiData() throws org.json.JSONException {
        String url = "https://developers.nonghyup.com/InquireTransactionHistory.nh";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        // 오늘 날짜
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);

        // Istuno 카운트
        Long id = 1L;
        Optional<Count> countOpt = countRepository.findById(id);
        if (countOpt.isEmpty()) {
            throw new RuntimeException("Count 데이터가 없습니다.");
        }
        String istuno = String.valueOf(countOpt.get().getIstuno());
        countRepository.incrementIstuno();

        // 요청 바디 생성
        String requestBody = """
        {
          "Header": {
            "ApiNm": "InquireTransactionHistory",
            "Tsymd": "%s",
            "Trtm": "112428",
            "Iscd": "002674",
            "FintechApsno": "001",
            "ApiSvcCd": "ReceivedTransferA",
            "IsTuno": "%s",
            "AccessToken": "a7ae51f122fe3639c8d4f890a2ec7e702eba6eeda9869c779f6463e54cf589b9"
          },
          "Bncd": "011",
          "Acno": "3020000011652",
          "Insymd": "20241001",
          "Ineymd": "%s",
          "TrnsDsnc": "A",
          "Lnsq": "DESC",
          "PageNo": "1",
          "Dmcnt": "100"
        }
        """.formatted(formattedDate, istuno, formattedDate);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // POST 요청 수행
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // 응답 상태 코드 검증
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException("API 호출 실패: 상태 코드 " + response.getStatusCode());
        }

        // 응답 본문 검증
        String responseBody = response.getBody();
        if (responseBody == null || !responseBody.contains("\"Rsms\":\"정상처리 되었습니다.\"")) {
            throw new RuntimeException("API 응답 데이터가 유효하지 않습니다.");
        }

        // JSONArray
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray recArray = jsonResponse.optJSONArray("REC");
        if (recArray == null) {
            throw new RuntimeException("API 응답에 'REC' 데이터가 없습니다.");
        }

        return recArray;
    }
}
