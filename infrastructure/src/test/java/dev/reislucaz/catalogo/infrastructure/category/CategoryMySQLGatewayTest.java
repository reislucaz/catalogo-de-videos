package dev.reislucaz.catalogo.infrastructure.category;

import dev.reislucaz.catalogo.domain.category.Category;
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
    public void givenAValidCategory_whenCallsCreate_thenReturnANewCategory() {
        final var expectedName = "Filmes de Terror";
        final var expectedDescription = "Categoria de Filmes de Terror Assustadores";
        final var expectedActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, categoryRepository.count());

        final var createdCategory = categoryMySQLGateway.create(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId().getValue(), createdCategory.getId().getValue());
        Assertions.assertEquals(aCategory.getName(), createdCategory.getName());
        Assertions.assertEquals(aCategory.getDescription(), createdCategory.getDescription());
        Assertions.assertEquals(aCategory.isActive(), createdCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), createdCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), createdCategory.getUpdatedAt());

        final var actualEntity = categoryRepository.findById(createdCategory.getId().getValue());

        Assertions.assertNotNull(actualEntity);

        actualEntity.ifPresent((entity) -> {
            Assertions.assertEquals(expectedName, entity.getName());
            Assertions.assertEquals(expectedDescription, entity.getDescription());
            Assertions.assertEquals(expectedActive, entity.isActive());
        });
    }
}
