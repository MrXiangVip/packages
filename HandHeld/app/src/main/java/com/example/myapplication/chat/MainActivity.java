package com.example.myapplication.chat;// MainActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ChatPresenter.View {
    private ChatPresenter presenter;
    private MessageAdapter adapter;
    private ProgressBar progressBar;
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 初始化控件
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        etInput = findViewById(R.id.et_input);
        Button btnSend = findViewById(R.id.btn_send);
        Button btnClear = findViewById(R.id.btn_clear);

        // 初始化适配器
        adapter = new MessageAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 初始化Presenter
        presenter = new ChatPresenter(this, this);
        presenter.loadMessages(); // 加载历史消息

        // 发送按钮点击事件
        btnSend.setOnClickListener(v -> {
            String content = etInput.getText().toString().trim();
            presenter.sendMessage(content);
            etInput.setText(""); // 清空输入框
        });

        // 清空按钮点击事件
        btnClear.setOnClickListener(v -> presenter.clearChat());
    }

    // 更新消息列表
    @Override
    public void updateMessageList(List<Message> messages) {
        adapter.setMessages(messages);
    }

    // 显示加载动画
    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    // 隐藏加载动画
    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    // 显示错误信息
    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
