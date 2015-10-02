# TestWeldInterceptors
Test interceptors not working in weld-2.2.16.SP1 and apache-tomcat-8.0.26

  @Inherited
  @InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Logged {}

@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static AtomicInteger logMethodEntryCount = new AtomicInteger(0);

	@AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        System.out.println("Entering method: " 
                + invocationContext.getMethod().getName() + " in class "
                + invocationContext.getMethod().getDeclaringClass().getName());
        logMethodEntryCount.incrementAndGet();
        return invocationContext.proceed();
    }
}

public class TestFilterInject implements Filter {
	@Override
	@Logged
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Entering filter");
		chain.doFilter(request, response);
	}
</code>
