package com.trantienptit.sev_user.chatbot23.dao;

import android.content.Context;
import android.util.Log;

import com.trantienptit.sev_user.chatbot23.R;
import com.trantienptit.sev_user.chatbot23.model.Feedbacks;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class FeedbackDAO {
	private Context context;
	public FeedbackDAO(Context context){
		this.context=context;
	}
	public boolean insertFeedbacks(Feedbacks Feedbacks){
		boolean result =false;
		String NAMESPACE = context.getResources().getString(R.string.NAME_SPACE);
		String URL = context.getResources().getString(R.string.URL_FB);
		String SOAP_METHOD = "insertFeedBack";
		String SOAP_ACTION = NAMESPACE + SOAP_METHOD;
		SoapObject request = new SoapObject(NAMESPACE, SOAP_METHOD);
		SoapObject obj=new SoapObject(NAMESPACE,"feedbacks");
		obj.addProperty("request",Feedbacks.getRequest());
		obj.addProperty("botResponse",Feedbacks.getBotResponse());
		obj.addProperty("customerResponse",Feedbacks.getCustomerResponse());
		obj.addProperty("rate",Feedbacks.getRate());
		request.addProperty("feebacks",obj);
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
			boolean response = (boolean) envelope.getResponse();
			result = response;
		} catch (IOException e) {
			Log.e("TAG", e.getMessage());
		} catch (XmlPullParserException e) {
			Log.e("TAG", e.getMessage());
		}
		return result;
	}
}
