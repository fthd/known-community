package com.known.web.filter;

import com.known.common.utils.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
  
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);  
    }  
  
  
    @Override  
    public String getQueryString() {  
        return clearXss(super.getQueryString());  
    }  
  
    @Override  
    public String getParameter(String name) {  
        return clearXss(super.getParameter(name));  
    }  
  
    @Override  
    public String[] getParameterValues(String name) {  
        String[] values = super.getParameterValues(name);  
        if(values != null) {  
            int length = values.length;  
            String[] escapseValues = new String[length];  
            for(int i = 0; i < length; i++){  
                escapseValues[i] = clearXss(values[i]);  
            }  
            return escapseValues;  
        }  
        return super.getParameterValues(name);  
    }  
    
    private String clearXss(String html){
    	if(StringUtils.isEmpty(html)){
    		return html;
    	}
    	html = StringEscapeUtils.escapeHtml4(html);
    	html = StringUtils.escapeHtml(html);
    	return html;
    }
      
}  