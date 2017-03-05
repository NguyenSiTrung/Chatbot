package com.trantienptit.sev_user.chatbot23.dao;

import android.content.Context;
import android.util.Log;


import com.trantienptit.sev_user.chatbot23.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by SEV_USER on 9/26/2016.
 */
public class ChatMessageDAO {
    private Context context;
    public ChatMessageDAO(Context context){
        this.context=context;
    }
    public String getTokenizer(String s) {
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
}
