package zero.crushserver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class ChatGptResponse {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private Usage usage;
    private List<Choice> choices;

    public static class Usage {
        @JsonProperty("prompt_tokens")
        private Integer promptTokens;
        @JsonProperty("completion_tokens")
        private Integer completionTokens;
        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }
    @Getter
    public static class Choice {
        private ChatGptMessage message;
        @JsonProperty("finish_reason")
        private String finishReason;
        private Integer index;
    }
}
