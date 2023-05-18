package com.interview.intuit.twitter.sentimentanalyzer.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.interview.intuit.twitter.sentimentanalyzer.model.Sentiment;
import com.interview.intuit.twitter.sentimentanalyzer.service.SentimentService;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OpenAISentimentService implements SentimentService {
    private static final Logger logger = LogManager.getLogger(OpenAISentimentService.class);
    @Value("${openai.api.key}")
    private String API_KEY;
    private String prompt;

    @Value("${intuit.keyword}")
    private String keyword;

    public OpenAISentimentService() {
        this.prompt = """
                Perform sentiment analysis on the given tweet about Intuit's product %s. Determine if the sentiment is negative(0) or positive(1), and calculate a sentiment score ranging from -1 (extremely negative) to +1 (extremely positive). Before making any sentiment analysis, please estimate the probability that the given tweet actually relates to Intuit product %s and return a score for it between 0 and 1.\s
                Please make sure that sentiment analysis has to be done in relation to %s in a tweet, not to tweet in general. For example if a tweet mentions %s in a positive way, but IRS in a negative way, be accurate to provide sentiment related to %s.\s
                Return the results in JSON format like {\\"contentRelatedScore\\": 0.8, \\"sentiment\\": 1, \\"score\\": 0.8 }.
                Please make sure to return just JSON, no explanation needed.
                                
                Tweet:
                """;
        this.prompt = String.format(prompt, keyword, keyword, keyword, keyword, keyword);
    }

    @Override
    public Sentiment analyzeSentiment(String text) {
        OpenAiService service = new OpenAiService(API_KEY);
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt + System.lineSeparator() + text);
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(100)
                .logitBias(new HashMap<>())
                .build();

        List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();
        if (choices.size() > 0) {
            ChatCompletionChoice choice = choices.get(0);
            try {
                JsonElement root = JsonParser.parseString(choice.getMessage().getContent());

                float contentRelatedScore = root.getAsJsonObject().get("contentRelatedScore").getAsFloat();
                int sentiment = root.getAsJsonObject().get("sentiment").getAsInt();
                float score = root.getAsJsonObject().get("score").getAsFloat();
                return new Sentiment(contentRelatedScore, sentiment, score);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Exception Parsing content from OPENAI API", e);
                logger.error("Content: " + choice.getMessage().getContent());
                return new Sentiment(-3, -3, -3);
            }
        }
        return new Sentiment(-2, -2, -2);
    }
}
