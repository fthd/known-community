package com.known.web.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//规定多个Filter的执行顺序,按照从小到大执行()中的值
@Order(2)
@WebFilter(urlPatterns = "/*", filterName = "xssFilter")
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