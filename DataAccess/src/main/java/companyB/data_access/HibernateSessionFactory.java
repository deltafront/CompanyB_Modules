package companyB.data_access;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by charlie on 1/17/15.
 */
public class HibernateSessionFactory
{
    public SessionFactory factory;
    public HibernateSessionFactory(String driver_class, String username, String password, String url, String dialect, List<Class> annotatedClasses)
    {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class",driver_class);
        configuration.setProperty("hibernate.connection.url",url);
        configuration.setProperty("hibernate.connection.username",username);
        configuration.setProperty("hibernate.connection.password",password);
        configuration.setProperty("hibernate.dialect",dialect);
        for(Class annotated_class : annotatedClasses)
        {
            configuration.addAnnotatedClass(annotated_class);
        }
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder(). applySettings(configuration.getProperties());
        factory = configuration.buildSessionFactory(builder.build());
    }
}
