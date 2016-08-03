package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;

import po.TextMessage;
import util.CheckUtil;
import util.MessageUtil;
import util.RebootUtil;
import util.WeixinUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeixinServlet extends HttpServlet {
	static Map<String, String> map1 = new HashMap<String, String>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String signature = req.getParameter("signature");
		System.out.println("signature=" + signature);
		String timestamp = req.getParameter("timestamp");
		System.out.println("timestamp=" + timestamp);
		String nonce = req.getParameter("nonce");
		System.out.println("nonce=" + nonce);
		String echostr = req.getParameter("echostr");
		System.out.println("echostr=" + echostr);

		PrintWriter out = resp.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String Recognition = map.get("Recognition");
			String PicUrl = map.get("PicUrl");
			String Lat= map.get("Latitude");
			String Lon= map.get("Longitude");
			
			if(null!=Lat||null!=Lon){
				map1.put("Lat", Lat);
				map1.put("Lon", Lon);
			}
			
			String message = null;
			if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				if ("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.firstMenu());
				}
//				else if ("2".equals(content)) {
//					message = MessageUtil.initNewsMessage(toUserName,
//							fromUserName);
//				}
				else if ("3".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.threeMenu());
				} else if ("4".equals(content)) {
					WeixinUtil.templetMessage(fromUserName, "");
				} else if ("?".equals(content) || "？".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
					// }
					// else if(content.startsWith("翻译")){
					// String word = content.replaceAll("^翻译", "").trim();
					// if("".equals(word)){
					// message = MessageUtil.initText(toUserName, fromUserName,
					// MessageUtil.threeMenu());
					// }else{
					// message = MessageUtil.initText(toUserName, fromUserName,
					// WeixinUtil.translate(word));
					// }
				}else {
					String request =MessageUtil.iniRebootMessage(map1.get("Lon"),map1.get("Lat"),content,toUserName, fromUserName,resp);
					if(!request.equals(content)){
						message = MessageUtil.initText(toUserName, fromUserName,request);
					}else{
						String str = "不能理解你的内容：" + content;
						message = MessageUtil.initText(toUserName, fromUserName,
								str);
					}
				}
			} else if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
				String eventType = map.get("Event");
			} else if (MessageUtil.MESSAGE_SUBSCRIBE.equals(msgType)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				} else if (MessageUtil.MESSAGE_CLICK.equals(msgType)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.menuText());
				} else if (MessageUtil.MESSAGE_VIEW.equals(msgType)) {
					String url = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName,
							url);
				} else if (MessageUtil.MESSAGE_SCANCODE.equals(msgType)) {
					String key = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName,
							key);
				} else if (MessageUtil.MESSAGE_LOCATION.equals(msgType)) {
				String label = map.get("Label");
				message = MessageUtil.initText(toUserName, fromUserName, label);
			} else if (MessageUtil.MESSAGE_VOICE.equals(msgType)) {

				if (Recognition.contains("天气")) {
					if (Recognition.length() > 3) {
						String city = Recognition.substring(0, 2);

						message = WeixinUtil.templetMessage(fromUserName, city)
								.toString();
					}
				} else {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.iniRebootMessage(map1.get("Lon"),map1.get("Lat"),Recognition,toUserName, fromUserName,resp));
					
//					String str = "不能理解你的内容：" + Recognition;
//					message = MessageUtil.initText(toUserName, fromUserName,
//							str);
				}

			} else if (MessageUtil.MESSAGE_IMAGE.equals(msgType)) {
				message = MessageUtil.faceMessage(fromUserName, toUserName,
						PicUrl);
			}

			
			System.out.println(map1.get("Lon"));
			System.out.println(map1.get("Lat"));
			
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

}