package url;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL; 

public class WebsiteReader {

	public String pageReader(String url){
		String res = "";
		try {
			URL url_ = new URL(url);
			BufferedInputStream tmp = new BufferedInputStream(url_.openStream());
			Reader reader =new InputStreamReader(tmp);
			int c; 
            while ((c = reader.read()) != -1) { 
                    res += ((char) c); 
            } 
		} catch (MalformedURLException e) {
			System.out.printf("wrong Url: "+url+"\n");//TODO
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return res;
	}
	
}
