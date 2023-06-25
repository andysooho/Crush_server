package zero.crushserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zero.crushserver.domain.ChatGptMessage;
import zero.crushserver.domain.ChatGptRequest;
import zero.crushserver.domain.ChatGptResponse;
import zero.crushserver.domain.RecommendRequest;

import java.util.List;

@Service
public class ChatService {
    @Value("${chatgpt.api.key}")
    private String apiKey;
    @Value("${chatgpt.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private ChatGptMessage chatGptMessage;

    @Autowired
    public ChatService(ChatGptMessage chatGptMessage) {
        this.chatGptMessage = chatGptMessage;
    }

    public ChatGptResponse getRecommendation(RecommendRequest recommendRequest) throws JsonProcessingException {
        String combinedMessage = getSystemRoleMessage() + "user_input:" + objectMapper.writeValueAsString(recommendRequest.getCloths())
                + ",options:" + objectMapper.writeValueAsString(recommendRequest.getOptions());
        System.out.println(combinedMessage); //TODO: remove
        chatGptMessage.setRole("user");
        chatGptMessage.setContent(combinedMessage);
        List<ChatGptMessage> chatGptMessages = List.of(chatGptMessage);
        ChatGptRequest chatGptRequest = ChatGptRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(chatGptMessages)
                .maxTokens(500)
                .temperature(0.7)
                .topP(1.0)
                .build();

        return getResponse(buildHttpEntity(chatGptRequest));
    }

    //사전설정 메시지
    public String getSystemRoleMessage() {
        return "You are a competent fashion stylist. Look at a given set of clothes and their conditions and recommend suitable combinations in Korean. It must be appropriate for the given options."
                + "Follow the output form unconditionally: [[\"cloth1\",\"cloth2\",\"describe Why we recommend it\"],[ \"cloth1\", \"cloth2\", \"describe Why we recommend it\"], ...]. Up to 3 combinations.";
    }

    public HttpEntity<ChatGptRequest> buildHttpEntity(ChatGptRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer " + apiKey);
        return new HttpEntity<>(chatRequest,headers);
    }

    //private static final RestTemplate restTemplate = new RestTemplate();
    public ChatGptResponse getResponse(HttpEntity<ChatGptRequest> chatRequestHttpEntity) {
        ResponseEntity<ChatGptResponse> responseEntity = new RestTemplate().postForEntity(
                "https://api.openai.com/v1/chat/completions",
                chatRequestHttpEntity,
                ChatGptResponse.class
        );
        return responseEntity.getBody();
    }
}
