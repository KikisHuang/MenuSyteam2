package com.menusystem.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURlConnection {
	static String t = null;


	public static String getHttpURlConnection(StringBuilder sb) throws IOException{


		URL url = null;
		url = new URL(sb.toString());
		
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) url.openConnection();
		// 设置请求方式
		conn.setRequestMethod("GET");
		
		conn.setReadTimeout(5000);// 请求超时时间
		conn.setConnectTimeout(5000);// 设置连接超时时间
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
		Log.i("网络状态码打印"," = "+conn.getResponseCode());
		if(conn.getResponseCode() == 200){
			
			// 获取对象流
			InputStream in = conn.getInputStream();
			// 创建字节输出对象流
			byte buffer[] = new byte[1024];

			int len = 0;

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			in.close();
			baos.close();
			
		
			t = new String(baos.toByteArray());
			
			
			}

		   if(t!=null){

			   return t;

		   }else{

			  return null;

		   }


		
			
		}

	}

