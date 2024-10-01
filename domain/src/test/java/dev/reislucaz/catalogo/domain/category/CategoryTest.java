package dev.reislucaz.catalogo.domain.category;

import dev.reislucaz.catalogo.domain.exceptions.DomainException;
import dev.reislucaz.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(actualCategory);

        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).aMessage());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = " ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).aMessage());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "Fi ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).aMessage());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = """
                A nível organizacional, a percepção das dificuldades desafia a capacidade de
                equalização do sistema de formação de quadros
                que corresponde às necessidades. Não obstante, a complexidade dos estudos efetuados
                auxilia a preparação e a composição do retorno esperado a longo prazo.
                """;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).aMessage());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveOk() {
        final var expectedName = "Filmes";
        final var expectedDescription = "  ";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);

        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveOk() {
        final var expectedName = "Filmes";
        final var expectedDescription = "  ";
        final var expectedIsActive = false;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);

        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNull(aCategory.getDeletedAt());
        Assertions.assertTrue(aCategory.isActive());

        final var actualCategory = aCategory.deactivate();

        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(
                "Film",
                "A categoria",
                expectedIsActive
        );

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    public void givenAValidCategory_whenCallUpdateIsActiveFalse_thenReturnCategoryInactive() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(
                "Film",
                "A categoria",
                true
        );

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNull(aCategory.getDeletedAt());

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryInactive() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(
                "Film",
                "A categoria",
                true
        );

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNull(aCategory.getDeletedAt());

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    }
}
