package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import po.AccessToken;
import po.Image;
import po.ImageMessage;
import po.Music;
import po.MusicMessage;
import po.News;
import po.NewsMessage;
import po.Speech;
import po.SpeechMessage;
import po.Templet;
import po.TempletMessage;
import po.TextMessage;
import po.TotalCount;
import po.WeatherTemplet;

import com.thoughtworks.xstream.XStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import po.News;
import po.NewsMessage;
import po.TextMessage;

/**
 * 消息封装类
 * 
 * @author Stephen
 *
 */
public class MessageUtil {

	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubsrcribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_OFFSET = "offset";
	public static final String MESSAGE_COUNT = "count";

	/**
	 * xml转为map集合
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request)
			throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);

		Element root = doc.getRootElement();

		List<Element> list = root.elements();

		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}

	/**
	 * 将文本消息对象转化为XML
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 组装文本消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName,
			String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setContent(content);
		text.setCreateTime(new Date().getTime());
		return textMessageToXml(text);
	}

	/**
	 * 主菜单
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		sb.append("1、小屋介绍\n");
		sb.append("2、屋主详情\n");
		sb.append("9、人脸识别\n\n");
		sb.append("回复？返回此菜单。");
		sb.append("您可以与机器人聊天哦！");
		return sb.toString();
	}

	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("http://htjsweixin.vip.natapp.cn/server3/credit/borrow.html");
		return sb.toString();
	}

	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("男，未婚-_-!");
		return sb.toString();
	}

	public static String threeMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("人脸检測使用指南").append("\n\n");
		buffer.append("发送一张清楚的照片，就能帮你分析出种族、年龄、性别等信息").append("\n");
		buffer.append("快来试试你是不是长得太着急");
		return buffer.toString();
	}

	// public static String threeMenu(){
	// StringBuffer sb = new StringBuffer();
	// sb.append("词组翻译使用指南\n\n");
	// sb.append("使用示例：\n");
	// sb.append("翻译足球\n");
	// sb.append("翻译中国足球\n");
	// sb.append("翻译football\n\n");
	// sb.append("回复？显示主菜单。");
	// return sb.toString();
	// }
	/**
	 * 图文信息转化为XML
	 * 
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 图片消息转为xml
	 * 
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 音乐消息转为xml
	 * 
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 语音消息转化为XML
	 * 
	 * @param speechMessage
	 * @return
	 */
	public static String speechMessageToXml(SpeechMessage speechMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", speechMessage.getClass());
		return xstream.toXML(speechMessage);
	}

	/**
	 * 图文消息组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,
			String fromUserName, String Title, String Description,
			String PicUrl, String Url) {
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle(Title);
		news.setDescription(Description);
		news.setPicUrl(PicUrl);
		news.setUrl(Url);

		newsList.add(news);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());

		message = newsMessageToXml(newsMessage);
		return message;
	}

	/**
	 * 组装图片消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName, String fromUserName) {
		String message = null;
		Image image = new Image();
		image.setMediaId("JTH8vBl0zDRlrrn2bBnMleySuHjVbMhyAo0U2x7kQyd1ciydhhsVPONbnRrKGp8m");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}

	/**
	 * 组装音乐消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName, String fromUserName) {
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("WsHCQr1ftJQwmGUGhCP8gZ13a77XVg5Ah_uHPHVEAQuRE5FEjn-DsZJzFZqZFeFk");
		music.setTitle("see you again");
		music.setDescription("速7片尾曲");
		music.setMusicUrl("http://zapper.tunnel.mobi/Weixin/resource/See You Again.mp3");
		music.setHQMusicUrl("http://zapper.tunnel.mobi/Weixin/resource/See You Again.mp3");

		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}

	/**
	 * 接受语音消息
	 * 
	 * @param ToUserName
	 * @param fromUserName
	 * @param MsgID
	 * @param MediaID
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static String initSpeechMessage(HttpServletRequest request)
			throws IOException, Exception {
		Map<String, String> map = xmlToMap(request);
		String message = null;
		Speech speech = new Speech();
		speech.setFormat("amr");
		speech.setMediaID(map.get("MediaID"));
		speech.setMsgID(map.get("MsgID"));
		speech.setRecognition(map.get("recognition"));

		SpeechMessage speechMessage = new SpeechMessage();
		speechMessage.setFromUserName(map.get("fromUserName"));
		speechMessage.setToUserName(map.get("ToUserName"));
		speechMessage.setMsgType(MESSAGE_VOICE);
		speechMessage.setCreateTime(new Date().getTime());
		speechMessage.setSpeech(speech);
		return message = speechMessageToXml(speechMessage);
	}

	public static JSONObject iniTempletMessage(String city, String fromUserName)
			throws IOException {

		String str = Weather.getWeatherInform(city);
		JSONObject jsonObject = JSONObject.fromObject(str);
		List list = (List) jsonObject.get("results");
		// System.out.println(list.get(0));
		List list1 = (List) JSONObject.fromObject(list.get(0)).get(
				"weather_data");

		WeatherTemplet demo2 = new WeatherTemplet();

		Templet d1 = new Templet();
		d1.setValue(JSONObject.fromObject(list.get(0)).get("currentCity")
				.toString());
		demo2.setFirst(d1);
		Templet d2 = new Templet();
		d2.setValue(JSONObject.fromObject(list1.get(0)).get("weather")
				.toString());
		demo2.setKeynote1(d2);
		Templet d3 = new Templet();
		d3.setValue(JSONObject.fromObject(list1.get(0)).get("temperature")
				.toString());
		demo2.setKeynote2(d3);
		Templet d4 = new Templet();
		d4.setValue(JSONObject.fromObject(list.get(0)).get("pm25").toString());
		demo2.setKeynote3(d4);
		Templet d5 = new Templet();
		d5.setValue("欢迎再次查询！");
		demo2.setRemark(d5);

		TempletMessage templet = new TempletMessage();
		templet.setTouser(fromUserName);
		templet.setTemplate_id("HtgYYasZSsQ9-QGisgCgIp_Likd5IwKyzNznJekSDC0");
		templet.setUrl("");
		templet.setData(demo2);

		JSONObject json = JSONObject.fromObject(templet);

		return json;

	}

	/*
	 * 获取地理位置
	 */
	public static String iniLocationMessage(String Lon, String Lat) {

		String str = AddressUtil.GetAddress(Lon, Lat);
		JSONObject jsonObject = JSONObject.fromObject(str);
		Object josn = jsonObject.opt("result");
		JSONObject address = JSONObject.fromObject(josn);
		String addr = (String) address.opt("formatted_address");

		return addr;

	}

	/**
	 * 微信人脸识别
	 * 
	 * @param request
	 * @return
	 */
	public static String faceMessage(String fromUserName, String toUserName,
			String PicUrl) {
		// 返回给微信server的xml消息
		String message = null;
		try {
			// // xml请求解析
			// Map<String, String> requestMap = MessageUtil.parseXml(request);
			// // 发送方帐号（open_id）
			// String fromUserName = requestMap.get("FromUserName");
			// // 公众帐号
			// String toUserName = requestMap.get("ToUserName");
			// // 消息类型
			// String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);

			// 图片消息

			// 取得图片地址
			String picUrl = PicUrl;
			// 人脸检測
			String detectResult = FacePlusUtil.detect(picUrl);
			textMessage.setContent(detectResult);

			message = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 将图灵机器人消息提取
	 */
	public static String iniRebootMessage(String Longitude, String Latitude,
			String content, String toUserName, String fromUserName,
			HttpServletResponse resp) throws IOException {

		String str = RebootUtil.rebootApi(Longitude, Latitude, content);
		JSONObject jsonObject = JSONObject.fromObject(str);
		String code = jsonObject.get("code").toString();
		PrintWriter out = resp.getWriter();
		String message = null;
		if ("100000".equals(code)) {
			message = jsonObject.get("text").toString();
		} else if ("200000".equals(code)) {
			String s1 = jsonObject.get("text").toString();
			String s2 = jsonObject.get("url").toString();
			message = s1 + "\r\n" + "地址：" + s2;
		} else if ("302000".equals(code)) {
			List list = (List) jsonObject.get("list");
			List<News> li=new ArrayList<News>();
			NewsMessage newsMessage = new NewsMessage();

			
			for (int i = 0; i < list.size(); i++) {

				News news = new News();
				news.setTitle(JSONObject.fromObject(list.get(i)).get("article")
						.toString());
				news.setDescription(JSONObject.fromObject(list.get(i))
						.get("source").toString());
				news.setPicUrl(JSONObject.fromObject(list.get(i)).get("icon")
						.toString());
				news.setUrl(JSONObject.fromObject(list.get(i)).get("detailurl")
						.toString());
				li.add(news);

			}
			newsMessage.setFromUserName(toUserName);
			newsMessage.setToUserName(fromUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setArticleCount(list.size());
			newsMessage.setArticles(li);
			newsMessage.setMsgType(MessageUtil.MESSAGE_NEWS);
			out.print(newsMessageToXml(newsMessage));
			out.close();

		} else if ("308000".equals(code)) {

			// message = jsonObject.get("text").toString();
			List list = (List) jsonObject.get("list");
			String Title = JSONObject.fromObject(list.get(0)).get("name")
					.toString();
			String Description = JSONObject.fromObject(list.get(0)).get("info")
					.toString();
			String PicUrl = JSONObject.fromObject(list.get(0)).get("icon")
					.toString();
			String Url = JSONObject.fromObject(list.get(0)).get("detailurl")
					.toString();
			String result = initNewsMessage(toUserName, fromUserName, Title,
					Description, PicUrl, Url);
			out.print(result);
			out.close();
			

		}

		return message;

	}
	/**
	 *组装获取模板消息 并转化成xml格式
	 * @return
	 */
	public static JSONObject initTotalCountText(String type, String offset,
			String count) {
		TotalCount text = new TotalCount();
		text.setType(type);
		text.setOffset(offset);
		text.setCount(count);
		
		StringBuilder sb = new StringBuilder();
		
//		sb.append("{\"count\":2,\"offset\":0,\"type\":\"image\"}");
		JSONObject json = JSONObject.fromObject(text);
		

		return json;
	}

}
