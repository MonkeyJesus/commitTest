package com.cwb.worm.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwb.worm.entity.Page;
import com.cwb.worm.thread.SearchThread;


public class Test {

	/**
	 * @param path
	 *            目标网页的链接
	 * @return 返回布尔值，表示是否正常下载目标页面
	 * @throws Exception
	 *             读取网页流或写入本地文件流的IO异常
	 */
//	public static boolean downloadPage(String path) throws Exception {
//		// 定义输入输出流
//		InputStream input = null;
//		OutputStream output = null;
//		
//		InputStreamReader ir = null;
//		
//		// 得到 post 方法
//		GetMethod getMethod = new GetMethod(path);
//		// 执行，返回状态码
//		int statusCode = httpClient.executeMethod(getMethod);
//		// 针对状态码进行处理
//		// 简单起见，只处理返回值为 200 的状态码
//		if (statusCode == HttpStatus.SC_OK) {
//			input = getMethod.getResponseBodyAsStream();
//			// 通过对URL的得到文件名
//			String filename = path.substring(path.lastIndexOf('/') + 1) + ".txt";
//			// 获得文件输出流
//			output = new FileOutputStream(filename);
//			// 输出到文件
//			ir = new InputStreamReader(input);
//			
//			BufferedReader br = new BufferedReader(ir);
//			StringBuffer s = new StringBuffer();
//			String s2 = "";
//			while((s2 = br.readLine())!=null){
//				s.append(s2);
//			}
//			
//			System.out.println(s);
////			int tempByte = -1;
////			while ((tempByte = input.read()) > 0) {
////				output.write(tempByte);
////			}
//			
//			// 关闭输入流
//			if (input != null) {
//				input.close();
//			}
//			// 关闭输出流
//			if (output != null) {
//				output.close();
//			}
//			return true;
//		}
//		return false;
//	}

	public static void main(String[] args) {
//		try {
//			Test.downloadPage("http://www.baidu.com");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Page page = new Page();
		page.setDeep(1);
		page.setUrl("http://www.baidu.com");
		
		
		List<String> urls = new ArrayList<String>();
		HttpURLConnection httpURLConnection = null;
		BufferedInputStream bis = null;
		URL url = null;
		
		
		try {
			Document doc = Jsoup.parse(new URL("http://www.baidu.com"), 10000);
			
			Elements es = doc.getElementsByTag("a");
			
			for (Element e : es) {
				try {
					url = new URL(e.attr("href"));
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();
					
					bis = new BufferedInputStream(httpURLConnection.getInputStream());
					String u = HttpURLConnection.guessContentTypeFromStream(bis);
					if("text/html".equals(u)){
						urls.add(e.attr("href"));
					}
					
//					System.out.println("file type:"+HttpURLConnection.guessContentTypeFromStream(bis)+"  ==  "+e.attr("href"));
				} catch (Exception e1) {
					continue;
				}
			}
			
			for (String s : urls) {
				System.out.println(s);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		List<Page> pages = new ArrayList<Page>();
		pages.add(page);
		
		Thread t = new SearchThread(pages);
		Thread t2 = new SearchThread(pages);
		Thread t3 = new SearchThread(pages);
		
		t.start();
		t2.start();
		t3.start();
//		
//		System.out.println(pages.size()+"===========");
	}
}
