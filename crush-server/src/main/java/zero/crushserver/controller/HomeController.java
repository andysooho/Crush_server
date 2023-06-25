package zero.crushserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import zero.crushserver.domain.ChatGptResponse;
import zero.crushserver.domain.RecommendRequest;
import zero.crushserver.service.ChatService;

@Controller
public class HomeController {
    private final ChatService chatService;
  
    @Autowired
    HomeController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/recommend")
    @ResponseBody
    public ChatGptResponse recommend(@RequestBody RecommendRequest recommendRequest) throws JsonProcessingException {
        return chatService.getRecommendation(recommendRequest);
    }
}
