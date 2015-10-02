package test;


import java.io.IOException;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/ServletInjectAndIntercept")
public class ServletInjectAndIntercept extends  HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ServletContext servletContext;
	
	@Inject
	private BeanManager beanManager;
	
	@Override
	@Logged
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		BeanManager cdi = CDI.current().getBeanManager();
		
		StringBuilder buf = new StringBuilder();
		buf
		.append("<html>")
		.append("<body>")
		.append("<h1>Interceptors not working</h1>")
		.append("<p>Is injection working?</p>")
		
		.append("<p><dt>beanManager (@Inject)</dt><dd>")
		.append(beanManager != null ? beanManager.toString() : "<b>NULL</b>")
		.append("</dd></p>")

		.append("<p><dt>beanManager (CDI.current().getBeanManager())</dt><dd>")
		.append(cdi != null ? cdi.toString() : "<b>NULL</b>")
		.append("</dd></p>")
		
		.append("<p><dt>servletContext (@Inject, CDI 1.1)</dt><dd>")
		.append(servletContext != null ? servletContext.toString() : "<b>NULL</b>")
		.append("</dd></p>")
		
		.append("<p><dt>doFilterCount (+1 for each call)</dt><dd>")
		.append(TestFilterInject.doFilterCount.intValue())
		.append("</dd></p>")
		
		.append("<p><dt>logMethodEntryCount (+2 for each call)</dt><dd>")
		.append(LoggedInterceptor.logMethodEntryCount.intValue())
		.append("<b> --&gt; NOT INCREMENTING!?</dd>")
		.append("</p></body></html>");
		
		resp.getWriter().write(buf.toString());
	}
}
