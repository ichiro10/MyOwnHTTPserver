package httpserver.itf.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpResponse;

/*
 * This class allows to build an object representing an HTTP static request
 */
public class HttpStaticRequest extends HttpRequest {
	static final String DEFAULT_FILE = "index.html";
	
	public HttpStaticRequest(HttpServer hs, String method, String ressname) throws IOException {
		super(hs, method, ressname);
	}
	
	public void process(HttpResponse resp) throws Exception {
		
	
		
	    String filename = m_hs.getFolder() + getRessname();
		if (filename.equals("FILES/")) {
			filename += DEFAULT_FILE;
		}
		
		
		File requestFile = new File(filename);
		
		if (!requestFile.exists()) {
			resp.setReplyError(404, "file doesn't exist");
	        return;
	    }

	    if (!requestFile.isFile()) {
	    	resp.setReplyError(404, "file doesn't exist");
	        return;
	    }
		
			resp.setReplyOk();
			resp.setContentLength((int)requestFile.length());
			resp.setContentType(getContentType(filename));
			
			
			PrintStream ps = resp.beginBody();
			FileInputStream fis = new FileInputStream(requestFile);
			ps.writeBytes(fis.readAllBytes());
			fis.close();
		}

}
