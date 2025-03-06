package edu.usfca.cs112.project1.study_buddy;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;

public class Model {
    private static final String key = "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"; 

    public String generate(String question) {
        JSONObject payload = new JSONObject();
        payload.put("model", "gpt-4o");
        payload.put("messages", new JSONArray().put(new JSONObject().put("role", "user").put("content", question)));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + key)
            .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            String content = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
            return content; 
        } 
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "Try Again";
        }
    }
}
