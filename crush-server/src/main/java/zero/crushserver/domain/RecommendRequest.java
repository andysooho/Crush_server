package zero.crushserver.domain;

import lombok.Data;

import java.util.List;

@Data
public class RecommendRequest {
    List<Cloth> cloths;
    Option options;

    @Data
    static class Cloth {
        String name;
        String color;
        String type;
        String thickness;
    }
    @Data
    static class Option {
        String weather;
        String occasion;
        String season;
        String style;
        String age;
        String sex;
    }
}
