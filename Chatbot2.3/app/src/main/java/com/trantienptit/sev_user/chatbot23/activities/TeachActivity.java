package com.trantienptit.sev_user.chatbot23.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trantienptit.sev_user.chatbot23.R;
import com.trantienptit.sev_user.chatbot23.dao.TeachDAO;
import com.trantienptit.sev_user.chatbot23.model.Teach;


public class TeachActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtRequest,edtResponse;
    TeachDAO teachDAO;
    Button btnTeach,btnCancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach);
        edtRequest= (EditText) findViewById(R.id.edtRequest);
        edtResponse= (EditText) findViewById(R.id.edtResponse);
        teachDAO=new TeachDAO(this);
        btnTeach= (Button) findViewById(R.id.btnTeach);
        btnCancle= (Button) findViewById(R.id.btnCancle);
        btnTeach.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        getWindow().setBackgroundDrawableResource(R.drawable.background_chatbot);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTeach:
                String req=edtRequest.getText().toString();
                String resp=edtResponse.getText().toString();
                if(req.equals("")||resp.equals("")){
                    Toast.makeText(this,"Nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }else {
                    Teach teach=new Teach(1,req,resp,1);
                    new AsyncAdd().execute(teach);
                }

                break;
            case R.id.btnCancle:
                finish();
                break;
        }
    }
    public class AsyncAdd extends AsyncTask<Teach, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Teach... params) {

            return teachDAO.insertFeedbacks(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Toast.makeText(TeachActivity.this,"Thêm dạy dỗ thành công", Toast.LENGTH_LONG).show();
                edtRequest.setText("");
                edtResponse.setText("");
            }else Toast.makeText(TeachActivity.this,"Thêm dạy dỗ không thành công", Toast.LENGTH_LONG).show();
        }
    }
}
