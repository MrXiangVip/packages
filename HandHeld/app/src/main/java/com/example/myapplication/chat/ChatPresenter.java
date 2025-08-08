package com.example.myapplication.chat;// ChatPresenter.java
import android.content.Context;
import java.util.List;

public class ChatPresenter {
    private final View view;
    private final MessageDao messageDao;
    private final OllamaService ollamaService;

    public interface View {
        void updateMessageList(List<Message> messages);
        void showLoading();
        void hideLoading();
        void showError(String error);
    }

    public ChatPresenter(View view, Context context) {
        this.view = view;
        this.messageDao = AppDatabase.getInstance(context).messageDao();
        this.ollamaService = new OllamaService();
    }

    // 加载历史消息
    public void loadMessages() {
        List<Message> messages = messageDao.getAllMessages();
        view.updateMessageList(messages);
    }

    // 发送消息
    public void sendMessage(String content) {
        if (content.isEmpty()) return;

        // 保存用户消息
        Message userMessage = new Message(content, "user", System.currentTimeMillis());
        messageDao.insertMessage(userMessage);
        loadMessages(); // 刷新列表

        // 显示加载状态
        view.showLoading();

        // 调用AI模型
        ollamaService.sendMessage(content, "qwen2.5:3b", new OllamaService.OnResponseListener() {
            @Override
            public void onSuccess(String response) {
                // 保存AI回复
                Message aiMessage = new Message(response, "ai", System.currentTimeMillis());
                messageDao.insertMessage(aiMessage);
                view.hideLoading();
                loadMessages(); // 刷新列表
            }

            @Override
            public void onError(String error) {
                view.hideLoading();
                view.showError("获取回复失败: " + error);
            }
        });
    }

    // 清空对话
    public void clearChat() {
        messageDao.clearAllMessages();
        loadMessages();
    }
}
