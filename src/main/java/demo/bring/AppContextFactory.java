package demo.bring;

import com.bobocode.svydovets.context.AnnotationConfigurationApplicationContext;
import com.bobocode.svydovets.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This is a simple factory that can provide default {@link ApplicationContext} or custom `by passed package.
 */
public final class AppContextFactory {

    private AppContextFactory() {
    }

    private static ApplicationContext defaultContext;
    private static Map<String, ApplicationContext> applicationContexts;

    /**
     * Provides {@link AnnotationConfigurationApplicationContext} that created from the root path.
     *
     * @return {@link AnnotationConfigurationApplicationContext}
     */
    public static ApplicationContext getContext() {
        if (Objects.isNull(defaultContext)) {
            defaultContext = create("demo.bring");
        }
        return defaultContext;
    }

    /**
     * Provides {@link AnnotationConfigurationApplicationContext} that can be created by specified path.
     * If the {@link AnnotationConfigurationApplicationContext} is created already then it will be taken from
     * the {@param applicationContextsContainer} {@link Map}.
     *
     * @param packageName name of package where contains required beans
     * @return {@link AnnotationConfigurationApplicationContext}
     */
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

    /**
     * Creates a new {@link AnnotationConfigurationApplicationContext} with specified {@param packageName}.
     *
     * @param packageName name of package where contains required beans
     * @return {@link AnnotationConfigurationApplicationContext}
     */
    public static ApplicationContext create(String packageName) {
        Objects.requireNonNull(packageName);
        return new AnnotationConfigurationApplicationContext(packageName);
    }
}
