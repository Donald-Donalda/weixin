package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AddressUtil {

	public static String GetAddress(String Lon, String lat) {
		
		String baiduUrl = "http://api.map.baidu.com/geocoder/v2/?ak=spOeA7yQTnH4GLzZ73PAvGx8&callback=renderReverse&location=LAT,LON&output=json";
		StringBuffer strBuf; 
		
		baiduUrl = baiduUrl.replace("LON", Lon);
		baiduUrl = baiduUrl.replace("LAT", lat);

		strBuf = new StringBuffer();

		try {
			URL url = new URL(baiduUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));// 转码。
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line + " ");    
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strBuf.delete(0,29).deleteCharAt(strBuf.length()-2).toString();

		// try
		// {
		// String url =
		// "http://api.map.baidu.com/geocoder/v2/?ak=E4805d16520de693a3fe707cdc962045&callback=renderReverse&location="
		// + lat + "," + lng + "&output=xml&pois=1";
		// WebRequest request = WebRequest.Create(url);
		// request.Method = "POST";
		// XmlDocument xmlDoc = new XmlDocument();
		// String sendData = xmlDoc.InnerXml;
		// byte[] byteArray = Encoding.Default.GetBytes(sendData);
		//
		// Stream dataStream = request.GetRequestStream();
		// dataStream.Write(byteArray, 0, byteArray.Length);
		// dataStream.Close();
		//
		// WebResponse response = request.GetResponse();
		// dataStream = response.GetResponseStream();
		// StreamReader reader = new StreamReader(dataStream,
		// System.Text.Encoding.GetEncoding("utf-8"));
		// string responseXml = reader.ReadToEnd();
		//
		// XmlDocument xml = new XmlDocument();
		// xml.LoadXml(responseXml);
		// String status =
		// xml.DocumentElement.SelectSingleNode("status").InnerText;
		// if (status == "0")
		// {
		//
		// XmlNodeList nodes =
		// xml.DocumentElement.GetElementsByTagName("formatted_address");
		// if (nodes.Count > 0)
		// {
		// return nodes[0].InnerText;
		// }
		// else
		// return "未获取到位置信息,错误码3";
		// }
		// else
		// {
		// return "未获取到位置信息,错误码1";
		// }
		// }
		// catch (System.Exception ex)
		// {
		// return "未获取到位置信息,错误码2";
		// }
	}

	
	public static void main(String[] args) {
		System.out.println(GetAddress("116.407413", "39.904214"));
	}
}
