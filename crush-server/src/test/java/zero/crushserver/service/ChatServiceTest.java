package zero.crushserver.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import zero.crushserver.domain.ChatGptMessage;
import zero.crushserver.domain.ChatGptRequest;
import zero.crushserver.domain.ChatGptResponse;

import java.util.List;

@SpringBootTest
class ChatServiceTest {

    @Autowired
    ChatService chatService;
    @Test
    void buildHttpEntity() {
    }

    @Test
    void getResponse() {
        ChatGptRequest chatGptRequest = ChatGptRequest.builder().build();
        chatGptRequest.setModel("gpt-3.5-turbo");
        ChatGptMessage chatGptMessage = new ChatGptMessage();
        chatGptMessage.setRole("user");
        chatGptMessage.setContent("안녕!");
        List<ChatGptMessage> list = List.of(chatGptMessage);
        chatGptRequest.setMessages(list);
        chatGptRequest.setMaxTokens(100);
        chatGptRequest.setTemperature(0.5);
        chatGptRequest.setTopP(1.0);
        HttpEntity<ChatGptRequest> chatGptRequestHttpEntity = chatService.buildHttpEntity(chatGptRequest);
        ChatGptResponse response = chatService.getResponse(chatGptRequestHttpEntity);
        System.out.println(response.getChoices().get(0).getMessage().getContent());
    }

    String system_role = "You are a competent fashion stylist. Look at a given set of clothes and their conditions and recommend suitable combinations in Korean. It must be appropriate for the given options."
    + "Follow the output form unconditionally: {[cloth1,cloth2,describe Why we recommend it],[ cloth1, cloth2, describe Why we recommend it], ...}. Up to 3 combinations.";

    String user_input = "{ \"cloths\": [ { \"name\": \"옷1\", \"color\": \"빨강\", \"type\": \"티셔츠\", \"thickness\": \"두꺼움\" }, { \"name\": \"자라에서 산거\", \"color\": \"파랑\", \"type\": \"바지\", \"thickness\": \"얇음\" }, { \"name\": \"옷2\", \"color\": \"검정\", \"type\": \"재킷\", \"thickness\": \"보통\" }, { \"name\": \"옷3\", \"color\": \"노랑\", \"type\": \"원피스\", \"thickness\": \"얇음\" }, { \"name\": \"옷4\", \"color\": \"회색\", \"type\": \"셔츠\", \"thickness\": \"보통\" }, { \"name\": \"옷5\", \"color\": \"연두색\", \"type\": \"스커트\", \"thickness\": \"보통\" }, { \"name\": \"옷6\", \"color\": \"흰색\", \"type\": \"블라우스\", \"thickness\": \"보통\" }, { \"name\": \"옷7\", \"color\": \"갈색\", \"type\": \"팬츠\", \"thickness\": \"두꺼움\" }, { \"name\": \"옷8\", \"color\": \"파스텔 핑크\", \"type\": \"롱 카디건\", \"thickness\": \"보통\" }, { \"name\": \"옷9\", \"color\": \"네이비\", \"type\": \"청바지\", \"thickness\": \"보통\" }, { \"name\": \"옷10\", \"color\": \"핑크\", \"type\": \"후드티\", \"thickness\": \"보통\" }, { \"name\": \"옷11\", \"color\": \"민트\", \"type\": \"반팔 셔츠\", \"thickness\": \"얇음\" }, { \"name\": \"옷12\", \"color\": \"라이트 그레이\", \"type\": \"크롭 탑\", \"thickness\": \"보통\" }, { \"name\": \"옷13\", \"color\": \"와인 레드\", \"type\": \"니트\", \"thickness\": \"두꺼움\" }, { \"name\": \"옷14\", \"color\": \"초록\", \"type\": \"롱 스커트\", \"thickness\": \"보통\" } ], \"options\": { \"weather\": \"sunny\", \"occasion\": \"casual\", \"season\": \"spring\", \"style\": \"sporty\" } }";

    @Test
    void getPromptEngineering() {
        ChatGptRequest chatGptRequest = ChatGptRequest.builder().build();
        chatGptRequest.setModel("gpt-3.5-turbo");
        ChatGptMessage chatGptMessage = new ChatGptMessage();
        chatGptMessage.setRole("user");
        chatGptMessage.setContent(system_role + user_input);
        List<ChatGptMessage> list = List.of(chatGptMessage);
        chatGptRequest.setMessages(list);
        chatGptRequest.setMaxTokens(500);
        chatGptRequest.setTemperature(0.5);
        chatGptRequest.setTopP(0.9);
        HttpEntity<ChatGptRequest> chatGptRequestHttpEntity = chatService.buildHttpEntity(chatGptRequest);
        ChatGptResponse response = chatService.getResponse(chatGptRequestHttpEntity);
        System.out.println(response.getChoices().get(0).getMessage().getContent());
    }
}