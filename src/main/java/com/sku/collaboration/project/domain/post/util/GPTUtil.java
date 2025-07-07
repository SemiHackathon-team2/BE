package com.sku.collaboration.project.domain.post.util;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class GPTUtil {

  @Value("${openai.api-key}")
  private String apiKey;

  @Value("${openai.model}")
  private String model;

  @Value("${openai.url}")
  private String url;

  private final RestTemplate restTemplate = new RestTemplate();

  public String summarize(String content) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiKey);

    Map<String, Object> message = Map.of(
        "role", "user",
        "content", "다음 글을 3줄로 요약해줘:\n" + content
    );

    Map<String, Object> body = Map.of(
        "model", model,
        "messages", List.of(message),
        "temperature", 0.7
    );

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

    ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

    log.info("[GPT 응답] {}", response.getBody()); // 👉 응답 로그 찍기

    Object choicesObj = response.getBody().get("choices");
    if (!(choicesObj instanceof List<?> choices) || choices.isEmpty()) {
      throw new RuntimeException("GPT 응답 파싱 오류: choices가 비어 있거나 잘못된 형식");
    }
    //List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
    //Map<String, Object> messageMap = (Map<String, Object>) choices.get(0).get("message");
    Map<String, Object> messageMap = (Map<String, Object>) ((Map<?, ?>) choices.get(0)).get("message");
    return (String) messageMap.get("content");
  }

  public String summarizeComments(List<String> comments) {
    String joined = String.join("\n", comments);

    String prompt = """
    다음은 어떤 게시글에 달린 댓글 목록이야. 이 댓글들의 전반적인 반응을 요약해줘.
    감정적인 표현은 피하고, 자주 등장하는 주제나 톤을 중립적으로 정리해줘.
    \n\n""" + joined;

    return summarize(prompt); // 너가 기존에 만든 gpt 호출 메서드 재활용
  }
}
