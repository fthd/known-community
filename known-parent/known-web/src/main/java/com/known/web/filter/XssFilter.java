package com.known.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*@WebFilter(urlPatterns = "/*", filterName = "xssFilter")*/
public class XssFilter implements Filter {
      
    public void init(FilterConfig filterConfig) {
    }  
  
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
    }  
  
    public void destroy() {  
    }  
}