package filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(initParams = {@WebInitParam(name = "encode", value = "UTF-8")}, urlPatterns = {"/*"})
public class CharacterFilter implements Filter {
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encode = filterConfig.getInitParameter("encode");
        if(null != encode){
            encoding = encode;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse){
            HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
            HttpServletResponse httpRes = (HttpServletResponse) servletResponse;
            httpReq.setCharacterEncoding(encoding);
            httpRes.setContentType("text/html;charset=" + encoding);
            filterChain.doFilter(new CharacterEncodingRequest(httpReq), servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
