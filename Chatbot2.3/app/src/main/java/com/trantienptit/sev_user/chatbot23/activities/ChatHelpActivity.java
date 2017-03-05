package com.trantienptit.sev_user.chatbot23.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import com.trantienptit.sev_user.chatbot23.R;
import com.trantienptit.sev_user.chatbot23.adapter.ChatAdapter;
import com.trantienptit.sev_user.chatbot23.dao.ChatMessageDAO;
import com.trantienptit.sev_user.chatbot23.model.ChatMessage;
import com.trantienptit.sev_user.chatbot23.model.MyBubble;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SEV_USER on 9/16/2016.
 */
public class ChatHelpActivity extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private ImageButton sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private MyBubble myBubble;
    private static boolean isAdd = true;
    private ChatMessageDAO chatMessageDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatMessageDAO=new ChatMessageDAO(this);
        initControls();
        getWindow().setBackgroundDrawableResource(R.drawable.background_chatbot);
        myBubble = MyBubble.getInstance(this.getApplicationContext(),chatHistory,adapter);
        if (myBubble != null){
            myBubble.hideBubble();
            Log.i("TAG-Hide","Hide");
            adapter=myBubble.getAdapter();
            chatHistory=myBubble.getChatHistory();
            messagesContainer.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (ImageButton) findViewById(R.id.chatSendButton);
        messageET.requestFocus();
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                messageET.setText("");
                displayMessage(chatMessage);
                new AsyncLoad().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, messageText);
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory() {

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Xin chào");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("Tôi có thể giúp gì bạn?");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ChatHelpActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

//    private String getTokenizer(String s) {
//        String result = "";
//        String NAMESPACE = getResources().getString(R.string.NAME_SPACE);
//        String URL = getResources().getString(R.string.URL);
//        String SOAP_METHOD = "tokenizerService";
//        String SOAP_ACTION = NAMESPACE + SOAP_METHOD;
//        SoapObject request = new SoapObject(NAMESPACE, SOAP_METHOD);
//        request.addProperty("sequence", s);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.setOutputSoapObject(request);
//        envelope.implicitTypes = true;
//        MarshalFloat marshal = new MarshalFloat();
//        marshal.register(envelope);
////        HttpTransportSE httpTransportSE=new HttpTransportSE(URL);
//        AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
//        try {
////            httpTransportSE.call(SOAP_ACTION,envelope);
//            androidHttpTransport.call(SOAP_ACTION, envelope);
//            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
//            result = response.toString();
//        } catch (IOException e) {
//            Log.e("TAG", e.getMessage());
//        } catch (XmlPullParserException e) {
//            Log.e("TAG", e.getMessage());
//        }
//        return result;
//    }

    public class AsyncLoad extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = chatMessageDAO.getTokenizer(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null || s.isEmpty())
                s = "Đang có lỗi xảy ra";
            ChatMessage msg = new ChatMessage();
            msg.setId(1);
            msg.setMe(false);
            msg.setMessage(s);
            msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            adapter.add(msg);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG","onDestroy");
        if (isAdd) {
            Log.i("TAG_BB", "Add");
            myBubble.addBubble();
            isAdd = false;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG","onPause");
        if (isAdd) {
            Log.i("TAG_BB", "Add-Pause");
            myBubble.addBubble();
            isAdd = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG","onResume");
        if (!isAdd) {
            Log.i("TAG_BB", "HIDE");
            myBubble.hideBubble();
            isAdd = true;
        }

    }
}
