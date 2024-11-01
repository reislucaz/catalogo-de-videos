package dev.reislucaz.catalogo.applcation;

import dev.reislucaz.catalogo.IntegrationTest;
import dev.reislucaz.catalogo.application.category.create.CreateCategoryUseCase;
import dev.reislucaz.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

// Class only used for checking integration tests and to know how the right way to do it.
@IntegrationTest
public class SampleIntegrationTest {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test() {
        Assertions.assertNotNull(createCategoryUseCase);
        Assertions.assertNotNull(categoryRepository);
    }
}
