package org.tbk.sbswdemo.config;

import com.google.common.reflect.ClassPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.tbk.sbswdemo.Application;
import org.tbk.sbswdemo.model.Oem;

import javax.persistence.Entity;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
@Configuration("repositoryConfig")
@EntityScan(
        basePackageClasses = {
                Application.class,
                Jsr310JpaConverters.class
        }
)
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        try {
            config.exposeIdsFor(Oem.class);
            ClassPathUtils.streamClassesAnnotatedWith(Oem.class, Entity.class)
                    .peek(clazz -> log.debug("enable @Id json mapping for entity {}", clazz.getSimpleName()))
                    .forEach(config::exposeIdsFor);
        } catch (IOException e) {
            throw new IllegalStateException("Could not exposeIds for @Entity classes");
        }
    }

    private static final class ClassPathUtils {

        public static Stream<? extends Class<?>> streamClassesAnnotatedWith(Class<?> anyClassInBasePackage, Class<?> annotation) throws IOException {
            final String packageName = anyClassInBasePackage.getPackage().getName();
            return ClassPath.from(anyClassInBasePackage.getClassLoader())
                    .getTopLevelClasses(packageName).stream()
                    .map(ClassPath.ClassInfo::getName)
                    .map(ClassPathUtils::classForNameOrThrow)
                    .filter(clazz -> Arrays.stream(clazz.getAnnotations())
                            .map(Annotation::annotationType)
                            .anyMatch(annotation::isAssignableFrom));
        }

        private static Class<?> classForNameOrThrow(String name) {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
