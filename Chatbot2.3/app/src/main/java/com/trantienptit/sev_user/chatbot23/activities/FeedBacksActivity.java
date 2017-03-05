package com.trantienptit.sev_user.chatbot23.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.trantienptit.sev_user.chatbot23.R;
import com.trantienptit.sev_user.chatbot23.dao.FeedbackDAO;
import com.trantienptit.sev_user.chatbot23.model.Feedbacks;


public class FeedBacksActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup mRadioGroup;
    private TextView mLbRequest,mLbResponse;
    private EditText mEdtFeedback;
    private Button btnFeedback,btnCancel;
    private int rate;
    private FeedbackDAO feedbackDAO;
    String msRequest,msBotResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_backs);
        Bundle bundle=getIntent().getBundleExtra("msg");
        msRequest=bundle.getString("request");
        msBotResponse=bundle.getString("response");
        connectView();
        feedbackDAO=new FeedbackDAO(this);
        getWindow().setBackgroundDrawableResource(R.drawable.background_chatbot);

    }
    public void connectView(){
        mRadioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        mLbRequest= (TextView) findViewById(R.id.lbRequest);
        mLbResponse= (TextView) findViewById(R.id.lbResponse);
        mEdtFeedback= (EditText) findViewById(R.id.edtFeedback);
        btnCancel= (Button) findViewById(R.id.btnCancle);
        btnFeedback= (Button) findViewById(R.id.btnFeedback);
        Log.i("TAG",msRequest+" : "+msBotResponse);
        mLbRequest.setText(msRequest);
        mLbResponse.setText(msBotResponse);
        btnFeedback.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        mEdtFeedback.setVisibility(View.GONE);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbBad:
                        mEdtFeedback.setVisibility(View.VISIBLE);
                        rate=3;
                        break;
                    case R.id.rbVeryGood:
                        rate=1;
                        mEdtFeedback.setVisibility(View.GONE);
                        break;
                    case R.id.rbGood:
                        rate=2;
                        mEdtFeedback.setVisibility(View.GONE);
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancle:
                finish();
                break;
            case R.id.btnFeedback:
                String cusResponse=mEdtFeedback.getText().toString();
                Feedbacks feedbacks=new Feedbacks(1,msRequest,msBotResponse,cusResponse,rate);
                new AsyncAdd().execute(feedbacks);
                break;
        }
    }
    public class AsyncAdd extends AsyncTask<Feedbacks,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Feedbacks... params) {

            return feedbackDAO.insertFeedbacks(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Toast.makeText(FeedBacksActivity.this,"Thêm dạy dỗ thành công",Toast.LENGTH_LONG).show();
                finish();
            }else Toast.makeText(FeedBacksActivity.this,"Thêm dạy dỗ không thành công",Toast.LENGTH_LONG).show();
        }
    }
}
