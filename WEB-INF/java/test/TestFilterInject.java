package test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns="/ServletInjectAndIntercept") 
public class TestFilterInject implements Filter {

	public static AtomicInteger doFilterCount = new AtomicInteger(0);
	
	@Override
	@Logged
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Entering filter");
		doFilterCount.incrementAndGet();
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {		
	}

	@Override
	public void destroy() {		
	}
}
