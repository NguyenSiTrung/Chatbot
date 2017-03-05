package com.trantienptit.sev_user.chatbot23.dao;

import android.content.Context;
import android.util.Log;


import com.trantienptit.sev_user.chatbot23.R;
import com.trantienptit.sev_user.chatbot23.model.Teach;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class TeachDAO {
	private Context context;
	public TeachDAO(Context context){
		this.context=context;
	}
	public boolean insertFeedbacks(Teach Teach){
		String NAMESPACE = context.getResources().getString(R.string.NAME_SPACE);
		String URL = context.getResources().getString(R.string.URL_TEACH);
		String SOAP_METHOD = "insertTeach";
		String SOAP_ACTION = NAMESPACE + SOAP_METHOD;
		SoapObject request = new SoapObject(NAMESPACE, SOAP_METHOD);
		SoapObject obj=new SoapObject(NAMESPACE,"Teach");
		obj.addProperty("request",Teach.getRequest());
		obj.addProperty("response",Teach.getResponse());
		obj.addProperty("rate",Teach.getState());
		request.addProperty("Teach",obj);
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
			if(response.toString().equals("true")){
				return true;
			}
			else return false;
		} catch (IOException e) {
//			Log.e("TAG", e.getMessage());
		} catch (XmlPullParserException e) {
			Log.e("TAG", e.getMessage());
		}
		return false;
	}
}
