import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.*;
import org.apache.catalina.startup.CatalinaBaseConfigurationSource;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.apache.tomcat.util.file.ConfigFileLoader;

import java.io.File;
import java.io.IOException;

/**
 * tomcat contains multiple servers;
 * server contains multiple services;
 *
 * engine is top level `container` in a Catalina hierarchy;
 * engine contains multiple hosts;
 * host contains multiple context;
 * context contains multiple wraps;
 */
public class TomcatEmbedApplication {
    public static void main(String[] args) throws LifecycleException, IOException {

        // create a tomcat
        Tomcat tomcat = new Tomcat();

        // create a server
        Server server = new StandardServer();
        File basedir = new File(System.getProperty("user.dir") + "/catalina-home");
        if (!basedir.exists()) {
            basedir.mkdir();
        }
        server.setCatalinaHome(basedir);
        ConfigFileLoader.setSource(new CatalinaBaseConfigurationSource(basedir, null));

        // create a service and config it
        Service service = new StandardService();
        service.setName("Ark's service");

        // create a connector and config it
        Connector connector = new Connector();
        connector.setPort(8080);

        // create an engine and config it
        Engine engine = new StandardEngine();
        engine.setDefaultHost("Ark's host");

        // create a host and config it
        Host host = new StandardHost();
        host.setName("Ark's host");

        // create a context
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());

        // create filter
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName(IndexFilter.class.getSimpleName());
        filterDef.setFilterClass(IndexFilter.class.getName());
        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName(IndexFilter.class.getSimpleName());
        filterMap.addURLPattern("/*");
        context.addFilterDef(filterDef);
        context.addFilterMap(filterMap);

        // create servlet
        Wrapper wrapper = new Tomcat.ExistingStandardWrapper(new IndexServlet());
        wrapper.setName("index");
        context.addChild(wrapper);
        context.addServletMappingDecoded("/*", "index");

        // build up
        host.addChild(context);
        engine.addChild(host);
        service.setContainer(engine);
        service.addConnector(connector);
        server.addService(service);

        // start
        server.start();
        tomcat.start();
        // await
        tomcat.getServer().await();
    }
}
