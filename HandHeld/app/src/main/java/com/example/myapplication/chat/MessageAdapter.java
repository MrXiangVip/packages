package com.example.myapplication.chat;// MessageAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);
        if ("user".equals(message.getRole())) {
            // 显示用户消息，隐藏AI消息
            holder.llUser.setVisibility(View.VISIBLE);
            holder.llAi.setVisibility(View.GONE);
            holder.tvUserContent.setText(message.getContent());
        } else {
            // 显示AI消息，隐藏用户消息
            holder.llUser.setVisibility(View.GONE);
            holder.llAi.setVisibility(View.VISIBLE);
            holder.tvAiContent.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llUser, llAi;
        TextView tvUserContent, tvAiContent;

        public ViewHolder(View itemView) {
            super(itemView);
            llUser = itemView.findViewById(R.id.ll_user);
            llAi = itemView.findViewById(R.id.ll_ai);
            tvUserContent = itemView.findViewById(R.id.tv_user_content);
            tvAiContent = itemView.findViewById(R.id.tv_ai_content);
        }
    }

    // 更新数据
    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }
}
