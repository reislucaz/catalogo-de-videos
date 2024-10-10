package dev.reislucaz.catalogo.application.category.update;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import dev.reislucaz.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnUpdatedCategory() {
        final var expectedId = "123";
        final var expectedName = Optional.of("Updated Filmes");
        final var expectedDescription = Optional.of("A categoria mais assistida atualizada");
        final var expectedIsActive = Optional.of(true);

        final var aCommand = new UpdateCategoryCommand(expectedId, expectedName, expectedDescription, expectedIsActive);

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        when(gateway.findById(any(CategoryID.class))).thenReturn(Optional.of(aCategory));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertTrue(actualOutput.isRight());

        if (actualOutput.isRight()) {
            Assertions.assertEquals(aCategory.getId().getValue(), actualOutput.get().id().getValue());
        }

        Mockito.verify(gateway, times(1))
                .update(argThat(aUpdatedCategory ->
                        Objects.equals(expectedName.get(), aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription.get(), aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive.get(), aUpdatedCategory.isActive())
                                && Objects.nonNull(aUpdatedCategory.getId())
                ));
    }

    @Test
    public void givenAValidCommand_whenCategoryIsInactive_shouldReturnUpdatedCategory() {
        final var expectedId = "123";
        final var expectedName = Optional.of("Filmes");
        final var expectedDescription = Optional.of("A categoria mais assistida");
        final var expectedIsActive = Optional.of(false);

        final var aCommand = new UpdateCategoryCommand(expectedId, expectedName, expectedDescription, expectedIsActive);

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        when(gateway.findById(any(CategoryID.class))).thenReturn(Optional.of(aCategory));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertTrue(actualOutput.isRight());

        if (actualOutput.isRight()) {
            Assertions.assertEquals(aCategory.getId().getValue(), actualOutput.get().id().getValue());
        }

        Mockito.verify(gateway, times(1))
                .update(argThat(aUpdatedCategory ->
                        Objects.equals(expectedName.get(), aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription.get(), aUpdatedCategory.getDescription())
                                && !aUpdatedCategory.isActive()  // isActive is false
                ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsError_shouldReturnError() {
        final var expectedId = "123";
        final var expectedName = Optional.of("Filmes");
        final var expectedDescription = Optional.of("A categoria mais assistida");
        final var expectedIsActive = Optional.of(true);
        final var expectedErrorMessage = "Error on Gateway";

        final var aCommand = new UpdateCategoryCommand(expectedId, expectedName, expectedDescription, expectedIsActive);

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        when(gateway.findById(any(CategoryID.class))).thenReturn(Optional.of(aCategory));
        when(gateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualResult = useCase.execute(aCommand);

        Assertions.assertTrue(actualResult.isLeft());

        Assertions.assertEquals(expectedErrorMessage, actualResult.getLeft().getErrors().get(0).aMessage());

        Mockito.verify(gateway, times(1))
                .update(argThat(aUpdatedCategory ->
                        Objects.equals(expectedName.get(), aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription.get(), aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive.get(), aUpdatedCategory.isActive())
                ));
    }
}
