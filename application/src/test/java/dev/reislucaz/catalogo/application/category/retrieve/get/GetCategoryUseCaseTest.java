package dev.reislucaz.catalogo.application.category.retrieve.get;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import dev.reislucaz.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetCategoryUseCaseTest {
    @Mock
    CategoryGateway categoryGateway;

    @InjectMocks
    DefaultGetCategoryUseCase useCase;

    @Test
    public void givenAValidCommand_whenCallsGetCategory_thenReturnValidCategory() {
        final var expectedName = "Terror";
        final var expectedDescription = "Filmes assustadores";
        final var expectedActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedActive);

        when(categoryGateway.findById(any(CategoryID.class))).thenReturn(Optional.of(aCategory));

        final var expectedResult = useCase.execute(GetCategoryCommand.with(aCategory.getId().getValue()));

        Assertions.assertEquals(expectedResult.id(), aCategory.getId().getValue());
        Assertions.assertEquals(expectedResult.name(), aCategory.getName());
        Assertions.assertEquals(expectedResult.description(), aCategory.getDescription());
        Assertions.assertEquals(expectedResult.created_at(), aCategory.getCreatedAt());
        Assertions.assertEquals(expectedResult.updated_at(), aCategory.getUpdatedAt());
        Assertions.assertEquals(expectedResult.deleted_at(), aCategory.getDeletedAt());
    }
}
