package by.achramionok.authentication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kirill on 30.03.2017.
 */
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Expose-Headers", "authorization, X-Custom");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, UserId");
        if(request.getMethod().equals("OPTIONS")){
            response.setHeader("Access-Control-Allow-Credentials","*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-XSRF-TOKEN, authorization, UserId");
            response.addHeader("Access-Control-Max-Age", "1");
            response.getWriter().print("OK");
            response.getWriter().flush();
        }else {
            chain.doFilter(req, response);
        }
    }

    @Override
    public void destroy() {

    }
}
