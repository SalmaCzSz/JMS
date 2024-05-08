package jeejms.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeejms.MyMessageProducer;

@WebServlet(urlPatterns = "/")
public class MyServlet extends HttpServlet {
	@EJB
	MyMessageProducer producer;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		producer.sendMessage("Hello message form JavaEE Server using JMS");
		resp.getWriter().write("Published the message");
	}
}
