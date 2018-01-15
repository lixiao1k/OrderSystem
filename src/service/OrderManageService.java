package service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface OrderManageService {
    public List getMyOrder(String userid);

    public void sentErrorMessage(String message, HttpServletRequest req) throws ServletException,IOException;

    public void sentMessage(String message, HttpServletRequest req) throws ServletException,IOException;

    public void forwordPage(String page, HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException;
}
