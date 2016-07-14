package com.cwb.worm.thread;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwb.worm.entity.Page;

public class SearchThread extends Thread{
	private List<Page> pages = new ArrayList<Page>();
	
	public SearchThread(List<Page> pages){
		this.pages = pages;
	}

	@Override
	public void run() {
		while(pages.size()<1000){
			
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
						
//						System.out.println("file type:"+HttpURLConnection.guessContentTypeFromStream(bis)+"  ==  "+e.attr("href"));
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
			
			Page page = new Page();
			page.setUrl(pages.size()+"");
			pages.add(page);
			System.out.println(page.getUrl());
		}
	}
}
