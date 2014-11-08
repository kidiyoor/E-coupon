package com.example.sg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Lent extends Activity {
	Button send;
	EditText et1,et2,et3,et4,et5;
	String result = null;
	InputStream is = null;
String resValue;
String key="";
String mobKey="";
String sentU="";
String datelimit="";
String recAmount="";
String message;
TextView bal;

@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.alent);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
		bal=(TextView)findViewById(R.id.bal);
		send=(Button)findViewById(R.id.button1);
		et1 = (EditText) findViewById(R.id.editText3);
		et5 = (EditText) findViewById(R.id.editText5);
		et2 = (EditText) findViewById(R.id.editText2);
		et3= (EditText) findViewById(R.id.editText1);
		et4= (EditText) findViewById(R.id.editText4);
		send1();
		send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number = et2.getText().toString();
				
				String add=et4.getText().toString();
				send();
					if(resValue.equalsIgnoreCase("error authenticating"))
					{
						Toast.makeText(getApplicationContext(), "error authenticating", Toast.LENGTH_SHORT).show();
					}
					else if(resValue.equalsIgnoreCase("success"))
					{
						Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
						
						sendSMS(number,message);
						sendEmail(add);
				
					}
					else if(resValue.equalsIgnoreCase("Low Balance"))
					{
						Toast.makeText(getApplicationContext(), "Low Balance", Toast.LENGTH_LONG).show();
			
					}
					else 
					{
						Toast.makeText(getApplicationContext(), "unsuccessfull"+resValue, Toast.LENGTH_LONG).show();
			
					}
			
					send1();
				}
			
		});
		
		
	}
	
	public void sendSMS(String number, String message){
		
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(number, null, message, null, null);
	}
	
	 protected void sendEmail(String add) {
	      Log.i("Send email", "");

	      String[] TO = {add};
	     // String[] CC = {"mcmohd@gmail.co"};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("text/plain");


	      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	      //emailIntent.putExtra(Intent.EXTRA_CC, CC);
	      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Got a E-gift coupon from"+sentU);
	      emailIntent.putExtra(Intent.EXTRA_TEXT, "You got "+recAmount+" from "+sentU+"\n"+" which can be availed within "+datelimit+"\n"+" Key : "+key)  ;

	      try {
	         startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	         finish();
	         Log.i("Finished sending email...", "");
	      } catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(Lent.this, 
	         "There is no email client installed.", Toast.LENGTH_SHORT).show();
	      }
	   }
	 void send(){
			try {
				 HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://192.168.0.199/egift/send.php");
			    //HttpPost httppost = new HttpPost("http://192.168.0.199/testj.json");
			   System.out.println("connect to website first time");
			     // Add your data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		            String acc = "1";
					nameValuePairs.add(new BasicNameValuePair("acc", acc));
		            String cname = et3.getText().toString();
					nameValuePairs.add(new BasicNameValuePair("cname", cname));
		            String uname = "gautham";
					nameValuePairs.add(new BasicNameValuePair("uname", uname));
		            String pass = "1";
					nameValuePairs.add(new BasicNameValuePair("pass", pass));
		            String amount = et1.getText().toString();
					nameValuePairs.add(new BasicNameValuePair("amount", amount));
					String email = et4.getText().toString();
					nameValuePairs.add(new BasicNameValuePair("email", email));
					String phno = et2.getText().toString();
					nameValuePairs.add(new BasicNameValuePair("phno", phno));
					String time = et5.getText().toString();
					nameValuePairs.add(new BasicNameValuePair("time", time));
					
		           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	       // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
	        
		       HttpEntity entity = response.getEntity();

	   	      is = entity.getContent();
	   	    System.out.println("..............");
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    }
	    

			download();
	try{
		System.out.println("inside try catch1");
		System.out.println("res="+result);
		//result="{\"a\":\"success\"}";
		//{"a":"success"}
		JSONObject json_data=new JSONObject(result);
		System.out.print("ll");
	//JSONArray jArray = new JSONArray(result);
	System.out.println("inside try catch2");

		int k=0;
	 //int y = jArray.length();
	// System.out.println("y="+y);

		System.out.println("inside try catch3");
		
	//JSONObject js = jArray.getJSONObject(0);
	System.out.println("inside try catch 3.5");

	System.out.println(json_data.getString("a"));
	resValue=json_data.getString("a");
	key=json_data.getString("b");
	mobKey=json_data.getString("c");
	datelimit=json_data.getString("d");
	recAmount=json_data.getString("e");
	message="Congrats ! You got "+recAmount+" from "+sentU+" which can be availed within "+datelimit+" Key : "+key  ;

	System.out.println("inside try catch4");
		


	} catch (JSONException e) {System.out.println("caught here");

	}

		}
		void download()
		{ System.out.println("in download");
			
			try{
				
					BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
				       StringBuilder sb = new StringBuilder();
			           String line = null;
			           int y=0;
			           while ((line = reader.readLine()) != null&&y==0) {
			                   sb.append(line + "\n");y=1;

			           }
			        
			           is.close();
			           result  = sb.toString();
			          
			           System.out.println("end of first half");
			   }catch(Exception e){
			           Log.e("log_tag", "Error converting result "+e.toString());
			           System.out.println("caught in download");
			           
			   }
		}
		
		
		
		
		 void send1(){
				try {
					 HttpClient httpclient = new DefaultHttpClient();
				    HttpPost httppost = new HttpPost("http://192.168.0.199/egift/getUserDetails.php?a=1");
				    //HttpPost httppost = new HttpPost("http://192.168.0.199/testj.json");
				   System.out.println("connect to website first time");
				     // Add your data
			        //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
			            
		       // Execute HTTP Post Request
			        HttpResponse response = httpclient.execute(httppost);
		        
			       HttpEntity entity = response.getEntity();

		   	      is = entity.getContent();
		   	    System.out.println("..............");
			    } catch (ClientProtocolException e) {
			        // TODO Auto-generated catch block
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			    }
		    

				download();
		try{
			System.out.println("inside try catch1");
			System.out.println("res="+result);
			//result="{\"a\":\"success\"}";
			//{"a":"success"}
			JSONObject json_data=new JSONObject(result);
			System.out.print("ll");
		//JSONArray jArray = new JSONArray(result);
		System.out.println("inside try catch2");
		//Toast.makeText(getApplicationContext(), json_data.getString("a"), 
			//	   Toast.LENGTH_LONG).show();
			int k=0;
		 //int y = jArray.length();
		// System.out.println("y="+y);

			System.out.println("inside try catch3");
			
		//JSONObject js = jArray.getJSONObject(0);
		System.out.println("inside try catch 3.5");

		System.out.println(json_data.getString("4"));
bal.setText(json_data.getString("4"));		
		System.out.println("inside try catch4");
			


		} catch (JSONException e) {System.out.println("caught here");

		}

			}
			void download1()
			{ System.out.println("in download");
				
				try{
					
						BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
					       StringBuilder sb = new StringBuilder();
				           String line = null;
				           int y=0;
				           while ((line = reader.readLine()) != null&&y==0) {
				                   sb.append(line + "\n");y=1;

				           }
				        
				           is.close();
				           result  = sb.toString();
				          
				           System.out.println("end of first half");
				   }catch(Exception e){
				           Log.e("log_tag", "Error converting result "+e.toString());
				           System.out.println("caught in download");
				           
				   }
			}

}
