import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeepseekConfig {

    @Bean(name = "deepseek-chat")
    public ChatLanguageModel deepseekChatModel() {
        // 这里用你项目里已经使用的 OpenAI/DeepSeek 工厂创建
        return OpenAiChatModel.builder()
                .apiKey("你的key，也可以从配置里读")
                .baseUrl("https://api.deepseek.com")
                .modelName("deepseek-chat")
                .build();
    }
}
