package com.trantienptit.sev_user.chatbot23.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.trantienptit.sev_user.chatbot23.R;
import com.trantienptit.sev_user.chatbot23.adapter.ChatAdapter;
import com.trantienptit.sev_user.chatbot23.bubble.BubbleLayout;
import com.trantienptit.sev_user.chatbot23.bubble.BubblesManager;
import com.trantienptit.sev_user.chatbot23.bubble.BubblesService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SEV_USER on 9/19/2016.
 */
public class MyBubble {
    private Context context;
    private BubbleLayout bubbleLayout;
    private static BubblesManager bubblesManager;
    public static boolean isOpenBubble = true;
    private RelativeLayout llContent;
    public static MyBubble myBubble;
    public ListView listView;
    public ImageButton btnSend;
    public EditText edtMsg;
    public ChatAdapter adapter;
    public ArrayList<ChatMessage> chatHistory;
    public  static ImageView ic_help;

    public static MyBubble getInstance(Context context, ArrayList<ChatMessage> chatHistory, ChatAdapter adapter) {
        if (myBubble != null) {
            myBubble.setChatHistory(chatHistory);
            return myBubble;
        }
        bubblesManager = new BubblesManager.Builder(context)
                .setTrashLayout(R.layout.notification_trash_layout)
                .build();
        bubblesManager.initialize();
        myBubble = new MyBubble(context, chatHistory, adapter);
        return myBubble;
    }

    public MyBubble(Context context, ArrayList<ChatMessage> chatHistory, ChatAdapter adapter) {
        this.context = context;
        this.chatHistory = chatHistory;
        this.adapter = adapter;
        addNewNotification();
        initControls();
    }

    public void setChatHistory(ArrayList<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public void addBubble() {
        // add bubble view into bubble manager
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        bubblesManager.addBubble(bubbleLayout, width, height/4);
//        loadDummyHistory();

    }

    public void addNewNotification() {
        bubbleLayout = (BubbleLayout) LayoutInflater.from(context)
                .inflate(R.layout.notification_layout, null);
        // this method call when user remove notification layout
        llContent = (RelativeLayout) bubbleLayout.findViewById(R.id.llContent);
        ic_help= (ImageView) bubbleLayout.findViewById(R.id.ic_help);
        ic_help.setColorFilter(Color.RED);
        llContent.setVisibility(View.GONE);
        bubbleLayout.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) {
                Toast.makeText(context, "Bubble removed !",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // this methoid call when cuser click on the notification layout( bubble layout)
        bubbleLayout.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                Toast.makeText(context, "Clicked !",
                        Toast.LENGTH_SHORT).show();
                if (isOpenBubble) {

                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) bubbleLayout.getLayoutParams();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.height = WindowManager.LayoutParams.MATCH_PARENT;
                    params.flags= WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;
                    params.format= PixelFormat.TRANSPARENT;
                    bubbleLayout.setLayoutParams(params);
                    llContent.setVisibility(View.VISIBLE);
                    edtMsg.requestFocus();
                    InputMethodManager imm = (InputMethodManager)
                            context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edtMsg, InputMethodManager.SHOW_IMPLICIT);
                    ic_help.setColorFilter(Color.GREEN);
                    isOpenBubble = false;
                } else {

                    llContent.setVisibility(View.GONE);
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) bubbleLayout.getLayoutParams();

                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.flags= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                    params.format= PixelFormat.TRANSLUCENT;
                    ic_help.setColorFilter(Color.RED);
                    bubbleLayout.setLayoutParams(params);
                    isOpenBubble = true;
                }
            }
        });
    }

    /**
     * Configure the trash layout with your BubblesManager builder.
     */
    private void initializeBubbleManager() {
        bubblesManager = new BubblesManager.Builder(context)
                .setTrashLayout(R.layout.notification_trash_layout)
                .build();
        bubblesManager.initialize();
    }

    public void hideBubble() {
        if (bubbleLayout != null)
            bubblesManager.removeBubble(bubbleLayout);
        Intent intent=new Intent(context,BubblesService.class);
        context.stopService(intent);
    }

    private void initControls() {

        listView = (ListView) bubbleLayout.findViewById(R.id.messagesContainer);
        edtMsg = (EditText) bubbleLayout.findViewById(R.id.messageEdit);
        btnSend = (ImageButton) bubbleLayout.findViewById(R.id.chatSendButton);
        edtMsg.requestFocus();
        edtMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Enter message ", Toast.LENGTH_LONG).show();
                Log.i("TAG-KEY", "OP");
            }
        });
        listView.setAdapter(adapter);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Send ", Toast.LENGTH_LONG).show();
                String messageText = edtMsg.getText().toString();
//                messageText = "Lỗi";
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                edtMsg.setText("");

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
        listView.setSelection(listView.getCount() - 1);
    }

    private void loadDummyHistory() {
        for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    private String getTokenizer(String s) {
        String result = "";
        String NAMESPACE = context.getResources().getString(R.string.NAME_SPACE);
        String URL = context.getResources().getString(R.string.URL);
        String SOAP_METHOD = "tokenizerService";
        String SOAP_ACTION = NAMESPACE + SOAP_METHOD;
        SoapObject request = new SoapObject(NAMESPACE, SOAP_METHOD);
        request.addProperty("sequence", s);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.implicitTypes = true;
        MarshalFloat marshal = new MarshalFloat();
        marshal.register(envelope);
//        HttpTransportSE httpTransportSE=new HttpTransportSE(URL);
        AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
        try {
//            httpTransportSE.call(SOAP_ACTION,envelope);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            result = response.toString();
        } catch (IOException e) {
            Log.e("TAG", e.getMessage());
        } catch (XmlPullParserException e) {
            Log.e("TAG", e.getMessage());
        }
        return result;
    }

    public class AsyncLoad extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = getTokenizer(params[0]);
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

    public ChatAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ChatAdapter adapter) {
        this.adapter = adapter;
    }

    public ArrayList<ChatMessage> getChatHistory() {
        return chatHistory;
    }
}
