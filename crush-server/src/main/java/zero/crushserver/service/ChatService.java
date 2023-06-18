package zero.crushserver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zero.crushserver.domain.ChatGptRequest;
import zero.crushserver.domain.ChatGptResponse;

@Service
public class ChatService {
    @Value("${chatgpt.api.key}")
    private String apiKey;


    //사전설정 메시지, 3.5-chatting API의 system_role과 유사하게 프롬프트를 구현.
    public String getSystemRoleMessage() {
        return "You are a competent fashion stylist. Look at a given set of clothes and their conditions and recommend suitable combinations in Korean. It must be appropriate for the given options."
                + "Follow the output form unconditionally: [[\"cloth1\",\"cloth2\",\"describe Why we recommend it\"],[ \"cloth1\", \"cloth2\", \"describe Why we recommend it\"], ...]. Up to 3 combinations.";
    }

    private static final RestTemplate restTemplate = new RestTemplate();
    public HttpEntity<ChatGptRequest> buildHttpEntity(ChatGptRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer " + apiKey);
        return new HttpEntity<>(chatRequest,headers);
    }

    public ChatGptResponse getResponse(HttpEntity<ChatGptRequest> chatRequestHttpEntity) {
        ResponseEntity<ChatGptResponse> responseEntity = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                chatRequestHttpEntity,
                ChatGptResponse.class
        );
        return responseEntity.getBody();
    }
}
