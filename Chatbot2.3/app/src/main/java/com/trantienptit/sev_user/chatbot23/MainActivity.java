package com.trantienptit.sev_user.chatbot23;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trantienptit.sev_user.chatbot23.activities.ChatActivity;
import com.trantienptit.sev_user.chatbot23.activities.ChatHelpActivity;
import com.trantienptit.sev_user.chatbot23.activities.TeachActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvSVMC;
    LinearLayout btnChat,btnChatHelp,btnTraining,btnAboutBot,btnAboutUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView();
    }
    public void connectView(){
        tvSVMC= (TextView) findViewById(R.id.tvSVMC);
        btnAboutBot= (LinearLayout) findViewById(R.id.btnAboutBot);
        btnAboutUs= (LinearLayout) findViewById(R.id.btnAboutUs);
        btnChat= (LinearLayout) findViewById(R.id.btnChat);
        btnChatHelp= (LinearLayout) findViewById(R.id.btnChatHelp);
        btnTraining= (LinearLayout) findViewById(R.id.btnTraining);
        //set view
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/Droid_Robot_JP2.ttf");
        tvSVMC.setTypeface(typeface);
        btnAboutBot.setOnClickListener(this);
        btnAboutUs.setOnClickListener(this);
        btnChat.setOnClickListener(this);
        btnChatHelp.setOnClickListener(this);
        btnTraining.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnChat:
                Intent intent=new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btnChatHelp:
                Intent intent2=new Intent(MainActivity.this, ChatHelpActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnTraining:
                Intent intent3=new Intent(MainActivity.this, TeachActivity.class);
                startActivity(intent3);
                break;
            default:
                intent=new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
        }
    }
}
