package util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import menu.Button;
import menu.ClickButton;
import menu.Menu;
import menu.ViewButton;
import po.AccessToken;
import po.TempletMessage;
import po.TextMessage;
import po.WeatherTemplet;
import trans.Data;
import trans.Parts;
import trans.Symbols;
import trans.TransResult;





	public class WeixinUtil {
		
		private static final String APPID = "wxa07cf7db4e391267";
		private static final String APPSECRET = "fa768ef155b9963f207207161dd849ca";
		private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

		private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		
		private static final String DOWNLOAD_URL ="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		
		private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		
		private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
		
		private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
		private static final String TEMPLET_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		private static final String WEATHER_URL = "http://php.weather.sina.com.cn/xml.php?city='北京'&password=DJOYnieT8234jlsK&day=0";
		
		private static final String SEMANTIC_UNDERSTAND_URL = "https://api.weixin.qq.com/semantic/semproxy/search?access_token=YOUR_ACCESS_TOKEN";
		
		private static final String FACEPLUS_URL = "http://apicn.faceplusplus.com/v2/detection/detect?url=URL&api_secret=API_SECRET&api_key=API_KEY";
		private static final String TOTAL_COUNT = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
		/**
		 * get请求
		 * @param url
		 * @return
		 * @throws ParseException
		 * @throws IOException
		 */
		public static JSONObject doGetStr(String url) throws ParseException, IOException{
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			JSONObject jsonObject = null;
			HttpResponse httpResponse = client.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
			return jsonObject;
		}
		
		/**
		 * POST请求
		 * @param url
		 * @param outStr
		 * @return
		 * @throws ParseException
		 * @throws IOException
		 */
		public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(url);
			JSONObject jsonObject = null;
			httpost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response = client.execute(httpost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject = JSONObject.fromObject(result);
			return jsonObject;
		}
		/**
		 * 文件上传
		 * @param filePath
		 * @param accessToken
		 * @param type
		 * @return
		 * @throws IOException
		 * @throws NoSuchAlgorithmException
		 * @throws NoSuchProviderException
		 * @throws KeyManagementException
		 */
		public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
			File file = new File(filePath);
			if (!file.exists() || !file.isFile()) {
				throw new IOException("文件不存在");
			}

			String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
			
			URL urlObj = new URL(url);
			//连接
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

			con.setRequestMethod("POST"); 
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); 

			//设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");

			//设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");

			byte[] head = sb.toString().getBytes("utf-8");

			//获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			//输出表头
			out.write(head);

			//文件正文部分
			//把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();

			//结尾部分
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

			out.write(foot);

			out.flush();
			out.close();

			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			String result = null;
			try {
				//定义BufferedReader输入流来读取URL的响应
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				if (result == null) {
					result = buffer.toString();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					reader.close();
				}
			}

			JSONObject jsonObj = JSONObject.fromObject(result);
			System.out.println(jsonObj);
			String typeName = "media_id";
			if(!"image".equals(type)){
				typeName = type + "_media_id";
			}
			String mediaId = jsonObj.getString(typeName);
			return mediaId;
		}
		
		
		/**
		 * 获取accessToken
		 * @return
		 * @throws ParseException
		 * @throws IOException
		 */
		public static AccessToken getAccessToken() throws ParseException, IOException{
			AccessToken token = new AccessToken();
			String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
			JSONObject jsonObject = doGetStr(url);
			if(jsonObject!=null){
				token.setToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			}
			return token;
		}
		
		/**
		 * 组装菜单
		 * @return
		 */
		public static Menu initMenu(){
			Menu menu = new Menu();
			ClickButton button11 = new ClickButton();
			button11.setName("mmm");
			button11.setType("click");
			button11.setKey("11");
			
			ViewButton button21 = new ViewButton();
			button21.setName("view菜单");
			button21.setType("view");
			button21.setUrl("htjsweixin.vip.natapp.cn/MicroCredit/server3/register.html");
			
			ClickButton button31 = new ClickButton();
			button31.setName("扫码事件");
			button31.setType("scancode_push");
			button31.setKey("31");
			
			ClickButton button32 = new ClickButton();
			button32.setName("地理位置");
			button32.setType("location_select");
			button32.setKey("32");
			
			Button button = new Button();
			button.setName("菜单");
			button.setSub_button(new Button[]{button31,button32});
			
			menu.setButton(new Button[]{button11,button21,button});
			return menu;
		}
		
		public static int createMenu(String token,String menu) throws ParseException, IOException{
			int result = 0;
			String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
			JSONObject jsonObject = doPostStr(url, menu);
			if(jsonObject != null){
				result = jsonObject.getInt("errcode");
			}
			return result;
		}
		
		public static JSONObject queryMenu(String token) throws ParseException, IOException{
			String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
			JSONObject jsonObject = doGetStr(url);
			return jsonObject;
		}
		
		public static int deleteMenu(String token) throws ParseException, IOException{
			String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
			JSONObject jsonObject = doGetStr(url);
			int result = 0;
			if(jsonObject != null){
				result = jsonObject.getInt("errcode");
			}
			return result;
		}
		
		public static String translate(String source) throws ParseException, IOException{
			String url = "http://openapi.baidu.com/public/2.0/translate/dict/simple?client_id=jNg0LPSBe691Il0CG5MwDupw&q=KEYWORD&from=auto&to=auto";
			url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
			JSONObject jsonObject = doGetStr(url);
			String errno = jsonObject.getString("errno");
			Object obj = jsonObject.get("data");
			StringBuffer dst = new StringBuffer();
			if("0".equals(errno) && !"[]".equals(obj.toString())){
				TransResult transResult = (TransResult) JSONObject.toBean(jsonObject, TransResult.class);
				Data data = transResult.getData();
				Symbols symbols = data.getSymbols()[0];
				String phzh = symbols.getPh_zh()==null ? "" : "中文拼音："+symbols.getPh_zh()+"\n";
				String phen = symbols.getPh_en()==null ? "" : "英式英标："+symbols.getPh_en()+"\n";
				String pham = symbols.getPh_am()==null ? "" : "美式英标："+symbols.getPh_am()+"\n";
				dst.append(phzh+phen+pham);
				
				Parts[] parts = symbols.getParts();
				String pat = null;
				for(Parts part : parts){
					pat = (part.getPart()!=null && !"".equals(part.getPart())) ? "["+part.getPart()+"]" : "";
					String[] means = part.getMeans();
					dst.append(pat);
					for(String mean : means){
						dst.append(mean+";");
					}
				}
			}else{
				dst.append(translateFull(source));
			}
			return dst.toString();
		}
		
		public static String translateFull(String source) throws ParseException, IOException{
			String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=jNg0LPSBe691Il0CG5MwDupw&q=KEYWORD&from=auto&to=auto";
			url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
			JSONObject jsonObject = doGetStr(url);
			StringBuffer dst = new StringBuffer();
			List<Map> list = (List<Map>) jsonObject.get("trans_result");
			for(Map map : list){
				dst.append(map.get("dst"));
			}
			return dst.toString();
		}
		
		public static String templetMessage(String fromUserName,String city) throws IOException{
			String tok = "PzKgwPBItaQU17yn35FxHF-VvCb11nFcg0yx0TBxiQ1xONNPE4f0yuHRBaux9Gbh52sbI6IAUkwH_OWH9T3KKoe8BqtF7exlDkf-IysqgscQVRiAEAGLH";
			AccessToken token = WeixinUtil.getAccessToken();
			String url = TEMPLET_URL.replace("ACCESS_TOKEN", token.getToken());
			
			 
			JSONObject jsonObject = doPostStr(url,JSONObject.fromObject(new MessageUtil().iniTempletMessage(city, fromUserName)).toString());

			return jsonObject.toString();
		}
		
		
		public static String totalCount() throws IOException{
			AccessToken token = WeixinUtil.getAccessToken();
			String url = TOTAL_COUNT.replace("ACCESS_TOKEN", "UCtXWwtvRfCv-6CNVI375kxLygaCuTmiL9Pp6B5AKPVFmU5X4HOlrJ4vPQne3DlC5o1HKcLI5JqtpnsmAb4m7FiXrDQdD0NixcRzjzwxoinfD_6vKPN0YQz7DY6ECWfPYNCeAHABQF");
			JSONObject jsonObject = doPostStr(url,MessageUtil.initTotalCountText("news", "0", "2").toString());
			
			System.out.println(MessageUtil.initTotalCountText("news", "0", "2").toString());
			String s = jsonObject.toString();
//			JSONObject s = MessageUtil.initTotalCountText("news","0","2");
			return s.toString();
			
		}
		
		
		
		
		
		
//		public static String weather(String fromUserName) throws ParseException, IOException{
//			WxTemplate t = new WxTemplate();  
//            t.setUrl("");  
//            t.setTouser(fromUserName); 
//            t.setTopcolor("#000000");  
//            t.setTemplate_id("Ud_X9b-VqhnCJheZhVw11IPfPlKdasR8dug4YyGmFso"); 
//            Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
//            TemplateData first = new TemplateData();  
//            first.setColor("#000000");  
//            first.setValue("***标题***");  
//            m.put("first", first);  
//            TemplateData name = new TemplateData();  
//            name.setColor("#000000");  
//            name.setValue("***名称***");  
//            m.put("name", name);  
//            TemplateData remark = new TemplateData();  
//            remark.setColor("blue");  
//            remark.setValue("***备注说明***");  
//            m.put("Remark", remark);  
//            t.setData(m);  
//		}
		
		
	}
