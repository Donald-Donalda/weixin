package test;

import java.io.IOException;

import util.Weather;
import util.WeixinUtil;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
//		Demo2 demo2=new Demo2();
//		
//		Demo1 d1=new Demo1();
//	    d1.setValue("恭喜你购买成功！");
//		demo2.setFirst(d1);
//		Demo1 d2=new Demo1();
//		d1.setValue("巧克力");
//		demo2.setKeynote1(d2);
//		Demo1 d3=new Demo1();
//		d3.setValue("39.8元");
//		demo2.setKeynote2(d3);
//		Demo1 d4=new Demo1();
//		d4.setValue("2014年9月22日");
//		demo2.setKeynote3(d4);
//		Demo1 d5=new Demo1();
//		d5.setValue("欢迎再次购买！");
//		demo2.setRemark(d5);
//		
//		Demo3 demo3=new Demo3();
//		demo3.setTouser("dsa");
//		demo3.setTemplate_id("dasdsad");
//		demo3.setUrl("dsadadsa");
//		demo3.setData(demo2);
//		
//		JSONObject json=JSONObject.fromObject(demo3);
//		System.out.println(Weather.getWeatherInform("北京"));
		try {
			System.out.println(WeixinUtil.totalCount());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
}
