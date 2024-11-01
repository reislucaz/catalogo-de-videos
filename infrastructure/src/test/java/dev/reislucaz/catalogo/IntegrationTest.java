package dev.reislucaz.catalogo;

import dev.reislucaz.catalogo.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited

@ActiveProfiles("test")
@SpringBootTest(classes = WebServerConfig.class)
@ExtendWith(CleanUpExtension.class)
public @interface IntegrationTest {
}
