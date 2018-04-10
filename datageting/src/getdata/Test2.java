package getdata;

import util.HttpClientUtil;

public class Test2 {

	public static void main(String[] args) {		
		int num = 0;
		String url = "";
		String[] quyu = {"350203","350205","350206","350211","350212","350213"};
		for (int i = 0; i < quyu.length; i++) {		
			boolean max = true;
			while(max){
				url = "http://zawb.fjgat.gov.cn/weixin/zhfw/czw_qwjs_cx.jsp?1=1&rowpage="+num+"&ss_qx="+quyu[i]+"&rPageSize=15";
				String a = HttpClientUtil.doGet(url);
				if (a.indexOf("<li") != -1) {
					num++;
					String[] b = a.split("</li>");		
					for (int j = 0; j < b.length - 1; j++) {
						String c = b[j].substring(b[j].indexOf("pic(") + 4, b[j].indexOf(");"));
						String d = c.substring(c.indexOf(",") + 1, c.length()).replace("'", "");
						System.out.println(d);
					}
				}else{
					max = false;
				}
			}
					
		}		
		
		
		
	}
}
