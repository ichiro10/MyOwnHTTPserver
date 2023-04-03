package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;

import httpserver.itf.HttpResponse;
import httpserver.itf.HttpRicmlet;
import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletRequestImpl extends HttpRicmletRequest {

	Hashtable<String, String> args = new Hashtable<String, String>();
	Hashtable<String, String> cookies = new Hashtable<String, String>();

		
		
	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br) throws IOException {
		super(hs, method, ressname, br);
		// TODO Auto-generated constructor stub
		/**String line = br.readLine();
		while(!(line.isEmpty())){
			if(line.startsWith("Cookie: ")) {
				int index = line.indexOf(";");
				String subCoockies = line.substring(8);
				if(index == -1) {
					String tmpCoockies[] = subCoockies.split("=");
					cookies.put(tmpCoockies[0], tmpCoockies[1]);
				}else{
					String tmpCoockies[] = subCoockies.split("; ");
					int i = 0;
					do {
						String tmpCoockies2[] = tmpCoockies[i].split("=");
						cookies.put(tmpCoockies2[0], tmpCoockies2[1]);
						
						i++;

					}while(i < tmpCoockies.length);

					}
				}
			line = br.readLine();
			}
			**/
		}

		
	
		
	public void parse_args(String arg_string) {
		if (arg_string == null) return;
		String[] pairs = arg_string.split("&");
		for (String couple : pairs) {
			int i = couple.indexOf("=");
			try {
				String key = URLDecoder.decode(couple.substring(0,i), "UTF-8");
				String value = URLDecoder.decode(couple.substring(i+1,couple.length()-1), "UTF-8");
				args.put(key, value);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
		return cookies.get(name);
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