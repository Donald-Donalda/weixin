package test;
import java.io.IOException;

import net.sf.json.JSONObject;
import po.AccessToken;
import po.TempletMessage;
import util.WeixinUtil;

public class WeixinTest {
	public static void main(String[] args) {
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			System.out.println("票据"+token.getToken());
			System.out.println("有效时间"+token.getExpiresIn());
			//String path = "D:/imooc.jpg";
			//String mediaId = WeixinUtil.upload(path, token.getToken(), "thumb");
			//System.out.println(mediaId);
			
//			String result = WeixinUtil.translate("my name is laobi");
			//String result = WeixinUtil.translateFull("");
//			System.out.println(result);
			
			String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
			int result = WeixinUtil.createMenu(token.getToken(), menu);
			if(result==0){
				System.out.println("创建菜单成功！");
			}else{
				System.out.println("错误码"+result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
