package zero.crushserver.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class ChatGptMessage {
    private String role;
    private String content;
}