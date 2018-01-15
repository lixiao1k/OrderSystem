package filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class UserVertifyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

//判断是否登陆的过滤器，由于可以游客登陆，所以/LoginServlet,
// /ShowOrdersServlet,/TravelLoginServlet不过滤。
// ShowErrorServlet等其他未来的页面都要参与过滤

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse){
            HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
            HttpServletResponse httpResp = (HttpServletResponse) servletResponse;
            boolean isLoginUri = httpReq.getRequestURI().equals(httpReq.getContextPath() + "/LoginServlet");
            boolean isShowOrderUri = httpReq.getRequestURI().equals(httpReq.getContextPath() + "/ShowOrdersServlet");
            boolean isTravelLoginServlet = httpReq.getRequestURI().equals(httpReq.getContextPath() + "/TravelLoginServlet");
            if(isLoginUri || isShowOrderUri || isTravelLoginServlet){
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                HttpSession session = httpReq.getSession(false);
                if(null == session){
                    httpResp.sendRedirect(httpReq.getContextPath() + "/LoginServlet");
                }else if(null == session.getAttribute("login")){
                    httpResp.sendRedirect(httpReq.getContextPath() + "/LoginServlet");
                }
            }
        }

    }

    @Override
    public void destroy() {

    }
}
