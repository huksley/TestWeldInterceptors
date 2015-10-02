# TestWeldInterceptors

Test interceptors not working in following setup:

  * weld-2.2.16.SP1
  * apache-tomcat-8.0.26
  * jdk1.7.0_79_x64
  * Windows 7

@WebFilter and @WebServlet methods is not intercepted in such configuration. 

```
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Logged {
}

@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {
    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        System.out.println("Entering LoggedInterceptor"); // FIXME: NEVER GOES HERE!!!
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
}
```

All tomcat log:

```
02-Oct-2015 10:46:21.541 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server version:        Apache Tomcat/8.0.26
02-Oct-2015 10:46:21.542 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server built:          Aug 18 2015 11:38:37 UTC
02-Oct-2015 10:46:21.542 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server number:         8.0.26.0
02-Oct-2015 10:46:21.542 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log OS Name:               Windows 7
02-Oct-2015 10:46:21.543 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log OS Version:            6.1
02-Oct-2015 10:46:21.543 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Architecture:          amd64
02-Oct-2015 10:46:21.543 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log JVM Version:           1.7.0_79-b15
02-Oct-2015 10:46:21.543 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log JVM Vendor:            Oracle Corporation
02-Oct-2015 10:46:21.544 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log CATALINA_BASE:         C:\apache-tomcat-8.0.26
02-Oct-2015 10:46:21.544 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log CATALINA_HOME:         C:\apache-tomcat-8.0.26
02-Oct-2015 10:46:21.544 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -XX:MaxPermSize=256m
02-Oct-2015 10:46:21.544 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -XX:+UseG1GC
02-Oct-2015 10:46:21.545 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Xmx2g
02-Oct-2015 10:46:21.545 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Xdebug
02-Oct-2015 10:46:21.545 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Xrunjdwp:transport=dt_socket,server=y,address=5090,suspend=n
02-Oct-2015 10:46:21.545 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Djava.util.logging.config.file=C:\apache-tomcat-8.0.26\conf\logging.properties
02-Oct-2015 10:46:21.545 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager
02-Oct-2015 10:46:21.546 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Djava.endorsed.dirs=C:\apache-tomcat-8.0.26\lib.jee
02-Oct-2015 10:46:21.546 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Dcatalina.base=C:\apache-tomcat-8.0.26
02-Oct-2015 10:46:21.546 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Dcatalina.home=C:\apache-tomcat-8.0.26
02-Oct-2015 10:46:21.546 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Djava.io.tmpdir=C:\apache-tomcat-8.0.26\temp
02-Oct-2015 10:46:21.682 INFO [main] org.apache.coyote.AbstractProtocol.init Initializing ProtocolHandler ["http-nio-8080"]
02-Oct-2015 10:46:21.705 INFO [main] org.apache.tomcat.util.net.NioSelectorPool.getSharedSelector Using a shared selector for servlet write/read
02-Oct-2015 10:46:21.707 INFO [main] org.apache.coyote.AbstractProtocol.init Initializing ProtocolHandler ["ajp-nio-8009"]
02-Oct-2015 10:46:21.709 INFO [main] org.apache.tomcat.util.net.NioSelectorPool.getSharedSelector Using a shared selector for servlet write/read
02-Oct-2015 10:46:21.710 INFO [main] org.apache.catalina.startup.Catalina.load Initialization processed in 579 ms
02-Oct-2015 10:46:21.730 INFO [main] org.apache.catalina.core.StandardService.startInternal Starting service Catalina
02-Oct-2015 10:46:21.731 INFO [main] org.apache.catalina.core.StandardEngine.startInternal Starting Servlet Engine: Apache Tomcat/8.0.26
02-Oct-2015 10:46:21.740 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\docs
02-Oct-2015 10:46:22.106 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\docs has finished in 365 ms
02-Oct-2015 10:46:22.106 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\examples
02-Oct-2015 10:46:22.503 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\examples has finished in 397 ms
02-Oct-2015 10:46:22.503 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\host-manager
02-Oct-2015 10:46:22.531 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\host-manager has finished in 28 ms
02-Oct-2015 10:46:22.532 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\manager
02-Oct-2015 10:46:22.560 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\manager has finished in 28 ms
02-Oct-2015 10:46:22.560 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\ROOT
02-Oct-2015 10:46:22.617 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\ROOT has finished in 57 ms
02-Oct-2015 10:46:22.620 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-nio-8080"]
02-Oct-2015 10:46:22.627 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["ajp-nio-8009"]
02-Oct-2015 10:46:22.629 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in 918 ms
```
