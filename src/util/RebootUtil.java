package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RebootUtil {

	/*
	 * 请求方式
	 * 
	 * http get
	 * 
	 * 请求参数说明
	 * 
	 * key：必须，32位字符串，开发者先注册帐号，激活之后即可获得
	 * 
	 * info：必须，请求内容，例如：打招呼“你好”，查天气“北京今天天气”等等，汉字内容需要用UTF-8编码
	 * 
	 * userid：非必须，上下文功能必须，长度1-32，字母或数字，此userid针对开发者自己的每一个用户
	 * 
	 * loc：非必须，位置信息，例如：北京中关村
	 * 
	 * lon：非必须，经度，例如：东经116.234632（小数点后保留6位），需要写为116234632
	 * 
	 * lat：非必须，纬度，例如：北纬40.234632（小数点后保留6位），需要写为40234632 调用示例
	 * URL=http://www.tuling123.com/openapi/api
	 */


	public static String rebootApi(String Longitude,String Latitude,String info) {
		
		String Long = Longitude.replace(".", "");
		String Lati = Latitude.replace(".", "");
		// 拼接图灵机器人的请求地址
		String queryUrl = "http://www.tuling123.com/openapi/api?key=KEY&info=INFO&lon=LON&lat=LAT&loc=LOC";
		// 对URL进行编码
		try {
			queryUrl = queryUrl.replace("KEY",
					"72e9856d8f6d0591eebff00f1fb078e4");
			queryUrl = queryUrl.replace("INFO",
					java.net.URLEncoder.encode(info, "UTF-8"));
			queryUrl = queryUrl.replace("LON", Long);
			queryUrl = queryUrl.replace("LAT", Lati);
			queryUrl = queryUrl.replace("LOC", MessageUtil.iniLocationMessage(Longitude, Latitude));
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuffer strBuf = new StringBuffer();  
		 try{  
	            URL url = new URL(queryUrl);  
	            URLConnection conn = url.openConnection();  
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。  
	            String line = null;  
	            while ((line = reader.readLine()) != null)  
	                strBuf.append(line + " ");  
	                reader.close();  
	        }catch(MalformedURLException e) {  
	            e.printStackTrace();   
	        }catch(IOException e){  
	            e.printStackTrace();   
	        }     

	        return strBuf.toString();  
	}
	
	
	
	public static void main(String[] args) {
//		System.out.println(rebootApi("116234632","40234632","你是男的还是女的"));
		MessageUtil.iniLocationMessage("40.234632", "116.234632");
//		System.out.println(Weather.getWeatherInform("北京"));
	}

}
