package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import httpserver.itf.HttpResponse;
import httpserver.itf.HttpRicmlet;
import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletRequestImpl extends HttpRicmletRequest {

	Hashtable<String, String> args = new Hashtable<String, String>();
    Map<String, String> cookies;
	public BufferedReader br;
		
		
	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br) throws IOException {
		super(hs, method, ressname, br);
		// TODO Auto-generated constructor stub
  		 String line = br.readLine();

	     cookies = new HashMap<>();
	     while(line != null && !line.equals("")) {
	    	 
	            if(line.startsWith("Cookie:")) {
	                String extractCookies = line.substring("Cookie: ".length());
	                String[] cookies = extractCookies.split(";");

	                for(String cookie : cookies) {
	                    String[] keyval = cookie.split("=");
	                    this.cookies.put(keyval[0].trim(), keyval[1]);
	                }
	            }
	            line = br.readLine();
	        }
		}

		
		

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArg(String name) {		
		// TODO Auto-generated method stub
		int index = this.m_ressname.indexOf("?");
		String line = this.m_ressname.substring(index + 1);
		StringTokenizer tokenizer = new StringTokenizer(line, "&");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.contains(name)) {
				index = token.indexOf("=");
				return token.substring(index + 1);
			}
		}
		return "";
	}
	

	@Override
	public String getCookie(String name) {
		return this.cookies.get(name);
	}

	@Override
	public void process(HttpResponse resp) throws Exception {
		// TODO Auto-generated method stub
		StringTokenizer tokenizer = new StringTokenizer(m_ressname,"?");
		String path = tokenizer.nextToken();
		tokenizer = new StringTokenizer(path,"/");
		tokenizer.nextToken();
		String clsname = tokenizer.nextToken() + "." + tokenizer.nextToken();
		try {
		   HttpRicmlet hr = m_hs.getInstance(clsname);
		   hr.doGet(this, (HttpRicmletResponse) resp);
		}catch (Exception e) {
			e.printStackTrace();
			resp.setReplyError(404, "ERROR");
		}
		
	}

}