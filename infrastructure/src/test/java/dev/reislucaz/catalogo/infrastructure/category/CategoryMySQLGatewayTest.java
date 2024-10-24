package dev.reislucaz.catalogo.infrastructure.category;

import dev.reislucaz.catalogo.infrastructure.MySQLGatewayTest;
import dev.reislucaz.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testInjectedDependencies() {
        Assertions.assertNotNull(categoryRepository);
        Assertions.assertNotNull(categoryMySQLGateway);
    }
}
