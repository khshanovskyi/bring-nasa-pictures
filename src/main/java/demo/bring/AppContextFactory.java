package demo.bring;

import com.bobocode.svydovets.context.AnnotationConfigurationApplicationContext;
import com.bobocode.svydovets.context.ApplicationContext;

import java.util.*;

public final class AppContextFactory {

    private AppContextFactory() {
    }

    private static ApplicationContext defaultContext;
    private static Map<String, ApplicationContext> applicationContexts;

    public static ApplicationContext getContext() {
        if (Objects.isNull(defaultContext)) {
            defaultContext = create("demo.bring");
        }
        return defaultContext;
    }

    public static ApplicationContext getContextWithSpecifiedPath(String packageName) {
        if (Objects.isNull(applicationContexts)) {
            applicationContexts = new HashMap<>();
        }

        if (applicationContexts.containsKey(packageName)) {
            return applicationContexts.get(packageName);
        } else {
            var context = create(packageName);
            applicationContexts.put(packageName, context);
            return context;
        }
    }

    public static ApplicationContext create(String packageName) {
        Objects.requireNonNull(packageName);
        return new AnnotationConfigurationApplicationContext(packageName);
    }
}
