# TestWeldInterceptors

Interceptor in filter and servlet not working in following setup:

  * weld-2.2.16.SP1 OR 2.3.0.Final
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
02-Oct-2015 10:53:38.267 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server version:        Apache Tomcat/8.0.26
02-Oct-2015 10:53:38.268 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server built:          Aug 18 2015 11:38:37 UTC
02-Oct-2015 10:53:38.269 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Server number:         8.0.26.0
02-Oct-2015 10:53:38.269 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log OS Name:               Windows 7
02-Oct-2015 10:53:38.269 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log OS Version:            6.1
02-Oct-2015 10:53:38.269 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Architecture:          x86
02-Oct-2015 10:53:38.270 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log JVM Version:           1.7.0_79-b15
02-Oct-2015 10:53:38.270 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log JVM Vendor:            Oracle Corporation
02-Oct-2015 10:53:38.270 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log CATALINA_BASE:         C:\apache-tomcat-8.0.26
02-Oct-2015 10:53:38.270 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log CATALINA_HOME:         C:\apache-tomcat-8.0.26
02-Oct-2015 10:53:38.271 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Djava.util.logging.config.file=C:\apache-tomcat-8.0.26\conf\logging.properties
02-Oct-2015 10:53:38.271 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager
02-Oct-2015 10:53:38.271 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Djava.endorsed.dirs=C:\apache-tomcat-8.0.26\endorsed
02-Oct-2015 10:53:38.271 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Dcatalina.base=C:\apache-tomcat-8.0.26
02-Oct-2015 10:53:38.271 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Dcatalina.home=C:\apache-tomcat-8.0.26
02-Oct-2015 10:53:38.272 INFO [main] org.apache.catalina.startup.VersionLoggerListener.log Command line argument: -Djava.io.tmpdir=C:\apache-tomcat-8.0.26\temp
02-Oct-2015 10:53:38.449 INFO [main] org.apache.coyote.AbstractProtocol.init Initializing ProtocolHandler ["http-nio-8080"]
02-Oct-2015 10:53:38.470 INFO [main] org.apache.tomcat.util.net.NioSelectorPool.getSharedSelector Using a shared selector for servlet write/read
02-Oct-2015 10:53:38.472 INFO [main] org.apache.coyote.AbstractProtocol.init Initializing ProtocolHandler ["ajp-nio-8009"]
02-Oct-2015 10:53:38.478 INFO [main] org.apache.tomcat.util.net.NioSelectorPool.getSharedSelector Using a shared selector for servlet write/read
02-Oct-2015 10:53:38.479 INFO [main] org.apache.catalina.startup.Catalina.load Initialization processed in 465 ms
02-Oct-2015 10:53:38.495 INFO [main] org.apache.catalina.core.StandardService.startInternal Starting service Catalina
02-Oct-2015 10:53:38.496 INFO [main] org.apache.catalina.core.StandardEngine.startInternal Starting Servlet Engine: Apache Tomcat/8.0.26
02-Oct-2015 10:53:38.504 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\docs
02-Oct-2015 10:53:38.791 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\docs has finished in 287 ms
02-Oct-2015 10:53:38.791 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\examples
02-Oct-2015 10:53:39.060 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\examples has finished in 269 ms
02-Oct-2015 10:53:39.060 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\host-manager
02-Oct-2015 10:53:39.110 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\host-manager has finished in 50 ms
02-Oct-2015 10:53:39.110 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\manager
02-Oct-2015 10:53:39.130 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\manager has finished in 20 ms
02-Oct-2015 10:53:39.131 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\ROOT
02-Oct-2015 10:53:39.144 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\ROOT has finished in 13 ms
02-Oct-2015 10:53:39.144 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory C:\apache-tomcat-8.0.26\webapps\TestWeldInterceptors
02-Oct-2015 10:53:39.670 INFO [localhost-startStop-1] org.apache.jasper.servlet.TldScanner.scanJars At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
02-Oct-2015 10:53:39.687 INFO [localhost-startStop-1] null.null WELD-ENV-001008: Initialize Weld using ServletContainerInitializer
02-Oct-2015 10:53:39.704 INFO [localhost-startStop-1] org.jboss.weld.bootstrap.WeldStartup.<clinit> WELD-000900: 2.2.16 (SP1)
02-Oct-2015 10:53:39.937 INFO [localhost-startStop-1] org.jboss.weld.bootstrap.WeldStartup.startContainer WELD-000101: Transactional services not available. Injection of @Inject UserTransaction not available. Transactional observers will be invoked synchronously.
02-Oct-2015 10:53:40.016 WARN [localhost-startStop-1] null.null WELD-001700: Interceptor annotation class javax.ejb.PostActivate not found, interception based on it is not enabled
02-Oct-2015 10:53:40.017 WARN [localhost-startStop-1] null.null WELD-001700: Interceptor annotation class javax.ejb.PrePassivate not found, interception based on it is not enabled
02-Oct-2015 10:53:40.144 INFO [localhost-startStop-1] org.jboss.weld.environment.tomcat.TomcatContainer.initialize WELD-ENV-001100: Tomcat 7+ detected, CDI injection will be available in Servlets, Filters and Listeners.
02-Oct-2015 10:53:40.456 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory C:\apache-tomcat-8.0.26\webapps\TestWeldInterceptors has finished in 1,312 ms
02-Oct-2015 10:53:40.514 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-nio-8080"]
02-Oct-2015 10:53:40.520 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["ajp-nio-8009"]
02-Oct-2015 10:53:40.522 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in 2041 ms
```
