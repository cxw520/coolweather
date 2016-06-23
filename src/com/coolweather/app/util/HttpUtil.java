package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connetion=null;
				try{
				   URL url=new URL(address);
				   connetion=(HttpURLConnection)url.openConnection();
				   connetion.setRequestMethod("GET");
				   connetion.setConnectTimeout(8000);
				   connetion.setReadTimeout(8000);
				   InputStream in=connetion.getInputStream();
				   BufferedReader reader=new BufferedReader(new InputStreamReader(in));
				   StringBuilder response=new StringBuilder();
				   String line;
				   while((line=reader.readLine())!=null){
					   response.append(line);
				   }
				   if(listener!=null){
					   listener.onFinish(response.toString());
				   }
				   
					
				}catch(Exception e){
					if(listener!=null){
						listener.onError(e);
					}
				}finally{
					if(connetion!=null){
						connetion.disconnect();
					}
				}
				
				
			}
		}).start();
	}

}
