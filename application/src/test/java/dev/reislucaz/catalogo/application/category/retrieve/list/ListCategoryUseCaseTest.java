package dev.reislucaz.catalogo.application.category.retrieve.list;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import dev.reislucaz.catalogo.domain.pagination.Pagination;
import dev.reislucaz.catalogo.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListCategoryUseCaseTest {

    @InjectMocks
    private DefaultListCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidQuery_whenCallsListCategories_shouldReturnCategories() {
        // Arrange
        final var categories = List.of(
                Category.newCategory("Filmes", "Categoria de filmes", true),
                Category.newCategory("Séries", "Categoria de séries", true)
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 2;
        final var expectedItems = categories;
        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                expectedItems
        );

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");

        when(categoryGateway.findAll(eq(aQuery))).thenReturn(expectedPagination);

        // Act
        final var actualResult = useCase.execute(aQuery);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedItems.size(), actualResult.items().size());

        verify(categoryGateway, times(1)).findAll(eq(aQuery));
    }

    @Test
    public void givenAValidQuery_whenHasNoCategories_shouldReturnEmptyPagination() {
        // Arrange
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 0;
        final var expectedItems = List.<Category>of();
        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                expectedItems
        );

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");

        when(categoryGateway.findAll(eq(aQuery))).thenReturn(expectedPagination);

        // Act
        final var actualResult = useCase.execute(aQuery);

        // Assert
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertTrue(actualResult.items().isEmpty());

        verify(categoryGateway, times(1)).findAll(eq(aQuery));
    }

    @Test
    public void givenAValidQuery_whenGatewayThrowsException_shouldReturnException() {
        // Arrange
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedErrorMessage = "Gateway error";

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");

        when(categoryGateway.findAll(eq(aQuery))).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act & Assert
        var exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            useCase.execute(aQuery);
        });

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        verify(categoryGateway, times(1)).findAll(eq(aQuery));
    }
}
