package demo.bring.config;

import com.bobocode.svydovets.annotation.Bean;
import com.bobocode.svydovets.annotation.Configuration;
import demo.bring.entity.Photo;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

@Configuration
public class BeanConfiguration {

    @Bean
    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "org.h2.Driver");
            settings.put(Environment.URL, "jdbc:h2:mem:test");
            settings.put(Environment.USER, "sa");
            settings.put(Environment.PASS, "");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "create-drop");

            org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(Photo.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

}
