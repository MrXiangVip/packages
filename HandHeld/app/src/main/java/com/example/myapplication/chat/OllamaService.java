package com.example.myapplication.chat;// OllamaService.java
import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OllamaService {
    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "http://127.0.0.1:11434/api/chat";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    // 回调接口
    public interface OnResponseListener {
        void onSuccess(String response);
        void onError(String error);
    }

    // 发送消息到本地模型
    public void sendMessage(String prompt, String model, OnResponseListener listener) {
        executor.execute(() -> {
            try {
                // 构建请求体
                JsonObject requestJson = new JsonObject();
                requestJson.addProperty("model", model);
                JsonObject message = new JsonObject();
                message.addProperty("role", "user");
                message.addProperty("content", prompt);
                requestJson.add("messages", new Gson().toJsonTree(new Object[]{message}));
                requestJson.addProperty("stream", false);

                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json"),
                        requestJson.toString()
                );

                // 发送请求
                Request request = new Request.Builder()
                        .url(baseUrl)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new IOException("请求失败: " + response.code());
                }

                // 解析响应
                String responseBody = response.body().string();
                JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
                String aiContent = jsonObject.getAsJsonArray("messages")
                        .get(jsonObject.getAsJsonArray("messages").size() - 1)
                        .getAsJsonObject()
                        .get("content")
                        .getAsString();

                // 主线程回调
                handler.post(() -> listener.onSuccess(aiContent));

            } catch (Exception e) {
                handler.post(() -> listener.onError(e.getMessage()));
            }
        });
    }
}
