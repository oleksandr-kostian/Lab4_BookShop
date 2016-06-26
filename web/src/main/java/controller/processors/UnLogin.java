package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for log out of user.
 *
 * @author Sasha Kostyan
 * @version %I%, %G%
 */
public class UnLogin implements GeneralProcess {

    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        /*HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }*/

        request.getSession().setAttribute(LoginUser.ATTRIBUTE_CUSTOMER,null);
        request.getSession().setAttribute(LoginUser.ATTRIBUTE_LOGIN, null);
        Commands.forward("/index.jsp", request, response);
    }

}
