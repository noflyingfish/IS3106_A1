package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import managedbean.UserSessionBean;

public class AuthFilter implements Filter {

    public AuthFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
//get the managed bean
        UserSessionBean bean;
        HttpSession session = request1.getSession(false);
        if (session == null) {
            bean = null;
        } else {
            bean = (UserSessionBean) session.getAttribute("userSessionBean");
        }
        if (bean == null || bean.getUserId() == -1L) {
//redirect to login page if user is not logged in
//and trying to access "secret/*" paths
            ((HttpServletResponse) response).sendRedirect(request1.getContextPath() + "/index.xhtml");
        } else {
//authenticated - continue
            chain.doFilter(request1, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//do nothing
    }

    @Override
    public void destroy() {
//do nothing
    }
}
