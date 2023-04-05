package httpserver.itf.impl;

import java.util.HashMap;
import java.util.Map;

import httpserver.itf.HttpSession;

public class Session implements HttpSession{
	Map<String, Object> data;
    int id;
    int timer;

    public Session(int id) {
        this.data = new HashMap<>();
        this.id = id;
        timer = 60;
    }

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		 timer = 60;
	     return Integer.toString(id);
	}

	@Override
	public Object getValue(String key) {
		// TODO Auto-generated method stub
		 timer = 60;
	    return data.get(key);
		
	}

	@Override
	public void setValue(String key, Object value) {
		// TODO Auto-generated method stub
		 timer = 60;
	     data.put(key, value);
		
	}
	public void time(){
	      timer = timer - 1;
	}
	

}
