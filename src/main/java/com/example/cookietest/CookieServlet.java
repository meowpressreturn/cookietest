package com.example.cookietest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CookieServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      Cookie cookie = null;  
      String setcookie = request.getParameter("setcookie");
      if("true".equalsIgnoreCase( setcookie )) {
        cookie = new Cookie("MYCOOKIE","abcde12345");
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);
      }
      if("clear".equalsIgnoreCase( request.getParameter("setcookie") )) {
        cookie = new Cookie("MYCOOKIE","clearing");
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(0); //delete pls
        response.addCookie(cookie);
      } if("session".equalsIgnoreCase( setcookie ) ) {
        request.getSession(true);
      } else if ("clearsession".equalsIgnoreCase(setcookie)) {
        HttpSession session = request.getSession(false);
        if(session!=null) {
          session.invalidate(); //doesnt clear the cookie though
        }
      }
      
      if("true".equalsIgnoreCase( request.getParameter("redirect") ) ) {
        response.sendRedirect( "cookieservlet" );
      } else {
        response.getWriter().println("<html><title>COOKIESERVLET</title><body>");
        if(request.isSecure()) {
          response.getWriter().println("<h1 style=\"color: green;\">Secure</h1>");
          response.getWriter().println("<a href=\"http://localhost:8080/cookietest/cookieservlet\">http</a><br/>");
        }
        else {
          response.getWriter().println("<h1 style=\"color: red;\">Not Secure</h1>");
          response.getWriter().println("<a href=\"https://localhost:8443/cookietest/cookieservlet\">https</a><br/>");
        }
        response.getWriter().println("<hr/>");
        response.getWriter().println("<a href=\"cookieservlet\">cookieservlet</a><br/>");
        response.getWriter().println("<a href=\"cookieservlet?redirect=true\">cookieservlet?redirect=true</a><br/>");
        response.getWriter().println("<a href=\"cookieservlet?setcookie=true\">cookieservlet?setcookie=true</a><br/>");
        response.getWriter().println("<a href=\"cookieservlet?setcookie=clear\">cookieservlet?setcookie=clear</a><br/>");
        response.getWriter().println("<a href=\"cookieservlet?setcookie=true&redirect=true\">cookieservlet?setcookie=true&redirect=true</a><br/>");
        response.getWriter().println("<a href=\"cookieservlet?setcookie=clearsession\">cookieservlet?setcookie=clearsession</a><br/>");
        response.getWriter().println("<a href=\"cookieservlet?setcookie=session&redirect=true\">cookieservlet?setcookie=session&redirect=true</a><br/>");
        Cookie[] cookies = request.getCookies();
        int cookieCount = cookies == null ? 0 : cookies.length;
        response.getWriter().println("Cookies submitted by browser in last request:" + cookieCount + "<br><ul>");
        for(int i=0; i<cookieCount; i++) {
          response.getWriter().println("<li>" + cookies[i].getName() + "=" + cookies[i].getValue() + "</li>");
        }
        response.getWriter().println("</ul>");
        if(cookie != null) {
          response.getWriter().println("<h2>Added cookie to response:" + cookie.getName() + " Secure=" + cookie.getSecure() + "</h2>");
        }
        response.getWriter().println("</body></html>");
      }
    }catch(Exception e) {
      e.printStackTrace();
      throw new ServletException("oops",e);
    }
  }

}



















