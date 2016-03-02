package controllers.utils.security;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecureResourcesStrategy {

    public boolean isAuthorized(HttpServletRequest req, HttpServletResponse res, ServletContext context)
            throws ServletException;

    public boolean isAuthorizedVisualize(HttpServletRequest req, HttpServletResponse res, ServletContext context)
            throws ServletException;
}
