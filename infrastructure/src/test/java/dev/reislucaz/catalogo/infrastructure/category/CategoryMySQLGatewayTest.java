package dev.reislucaz.catalogo.infrastructure.category;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.pagination.SearchQuery;
import dev.reislucaz.catalogo.infrastructure.MySQLGatewayTest;
import dev.reislucaz.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import dev.reislucaz.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Test
    public void givenAValidCategory_whenCallsUpdate_thenReturnUpdatedCategory() {
        final var initialName = "Filmes";
        final var initialDescription = "Categoria de Filmes";
        final var initialIsActive = true;

        final var aCategory = Category.newCategory(initialName, initialDescription, initialIsActive);

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        final var expectedName = "Filmes de Ação";
        final var expectedDescription = "Categoria de Filmes de Ação";
        final var expectedIsActive = false;

        final var updatedCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = categoryMySQLGateway.update(updatedCategory);

        Assertions.assertEquals(aCategory.getId().getValue(), actualCategory.getId().getValue());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());

        final var persistedCategory = categoryRepository.findById(actualCategory.getId().getValue()).get();

        Assertions.assertEquals(expectedName, persistedCategory.getName());
        Assertions.assertEquals(expectedDescription, persistedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, persistedCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), persistedCategory.getCreatedAt());
        Assertions.assertEquals(actualCategory.getUpdatedAt(), persistedCategory.getUpdatedAt());
    }

    @Test
    public void givenAValidCategory_whenCallsDelete_thenDeleteCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria de Filmes";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        final var categoryId = aCategory.getId();

        categoryMySQLGateway.deleteById(categoryId);

        Assertions.assertEquals(0, categoryRepository.count());

        final var deletedCategory = categoryRepository.findById(categoryId.getValue());
        Assertions.assertTrue(deletedCategory.isEmpty(), "Category should be deleted and not found in the repository");
    }

    @Test
    public void givenAValidCategory_whenCallsFindById_thenReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria de Filmes";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        Assertions.assertEquals(1, categoryRepository.count());

        final var categoryId = aCategory.getId();

        final var optionalCategory = categoryMySQLGateway.findById(categoryId);

        Assertions.assertTrue(optionalCategory.isPresent(), "Category should be present");

        final var foundCategory = optionalCategory.get();

        Assertions.assertEquals(aCategory.getId().getValue(), foundCategory.getId().getValue());
        Assertions.assertEquals(expectedName, foundCategory.getName());
        Assertions.assertEquals(expectedDescription, foundCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, foundCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), foundCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), foundCategory.getUpdatedAt());
    }

    @Test
    public void givenPersistedCategories_whenCallsFindAll_thenReturnPaginatedCategories() {
        final var categories = List.of(
                Category.newCategory("Filmes de Ação", null, true),
                Category.newCategory("Filmes de Terror", null, true),
                Category.newCategory("Documentários", null, true)
        );

        categories.forEach(category -> categoryRepository.saveAndFlush(CategoryJpaEntity.from(category)));

        Assertions.assertEquals(3, categoryRepository.count());

        final var searchQuery = new SearchQuery(0, 10, "", "name", "asc");

        final var pageResult = categoryMySQLGateway.findAll(searchQuery);

        Assertions.assertEquals(0, pageResult.currentPage());
        Assertions.assertEquals(10, pageResult.perPage());
        Assertions.assertEquals(3, pageResult.total());
        Assertions.assertEquals(3, pageResult.items().size());

        final var expectedNames = List.of("Documentários", "Filmes de Ação", "Filmes de Terror");

        final var actualNames = pageResult.items().stream()
                .map(Category::getName)
                .toList();

        Assertions.assertEquals(expectedNames, actualNames);
    }
}
