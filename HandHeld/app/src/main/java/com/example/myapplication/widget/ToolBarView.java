package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.zip.Inflater;

public class ToolBarView extends LinearLayout implements View.OnClickListener {

    ImageButton add,  cloud, clear, close, save;

    public ToolBarView(Context context) {
        this(context, null);
    }

    public ToolBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ToolBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ToolBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.tool_bar_view, this, true);
        setOrientation( VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundResource(R.drawable.bg_editor_dialog);

        add = findViewById(R.id.add);
        cloud = findViewById(R.id.cloud);
        clear =findViewById(R.id.clear);
        close = findViewById(R.id.close);
        save = findViewById(R.id.save);

        add.setOnClickListener( this);
    }

    @Override
    public void onClick(View v) {
        View parent= (View) getParent();
        if(v.getId()==R.id.add){
            ((DialogContainer)(parent.findViewById(R.id.dialogContainer))).show(CreateInputConfigPanel.class);
        }else if( v.getId()==R.id.cloud){
            ((DialogContainer)(parent.findViewById(R.id.dialogContainer))).show(CloudConfigView.class);
//            DialogContainer.show( CloudConfigView.class);
        }else if( v.getId()==R.id.clear){
            ((DialogContainer)(parent.findViewById(R.id.dialogContainer))).show(ClearDialog.class);
//            DialogContainer.show( ClearDialog.class);
        }else if( v.getId() ==R.id.close){
            ((DialogContainer)(parent.findViewById(R.id.dialogContainer))).show(ExitDialog.class);
//            DialogContainer.show( ExitDialog.class);
        }else if( v.getId()==R.id.save){
//            DialogContainer.show()
        }
    }
}
