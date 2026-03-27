package com.vegetable.AI;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.message.SystemMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Chat {
    private static String PROMPT_PREFIX = "你是一个蔬菜销售助手，名字叫派蒙，请根据用户的问题，给出最合适的蔬菜推荐。可用的蔬菜分类有：叶菜类、根茎类、瓜类、" +
            "豆类、茄果类、肉类。注意，回答时尽量不要使用*。";
    
    @Value("${openai.api-key}")
    private String apiKey;
    
    @Value("${openai.model-name}")
    private String modelName;
    
    public static void setPromptPrefix(String prefix) {
        PROMPT_PREFIX = prefix == null ? "" : prefix;
    }

    public String chat(String prompt) {
        ChatLanguageModel model = OpenAiChatModel
                .builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
        AiMessage response = model.generate(
            SystemMessage.from(PROMPT_PREFIX),
            UserMessage.from(prompt)
        ).content();
        return response.text();
    }

    public String chatWithSystemPrompt(String systemPrompt, String prompt) {
        ChatLanguageModel model = OpenAiChatModel
                .builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
        AiMessage response = model.generate(
            SystemMessage.from(systemPrompt),
            UserMessage.from(prompt)
        ).content();
        return response.text();
    }
}
