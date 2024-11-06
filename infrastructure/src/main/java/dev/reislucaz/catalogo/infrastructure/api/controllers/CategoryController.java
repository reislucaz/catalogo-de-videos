package dev.reislucaz.catalogo.infrastructure.api.controllers;

import dev.reislucaz.catalogo.application.category.create.CreateCategoryCommand;
import dev.reislucaz.catalogo.application.category.create.CreateCategoryOutput;
import dev.reislucaz.catalogo.application.category.create.CreateCategoryUseCase;
import dev.reislucaz.catalogo.application.category.retrieve.get.GetCategoryCommand;
import dev.reislucaz.catalogo.application.category.retrieve.get.GetCategoryUseCase;
import dev.reislucaz.catalogo.domain.pagination.Pagination;
import dev.reislucaz.catalogo.domain.validation.handler.NotificationHandler;
import dev.reislucaz.catalogo.infrastructure.api.CategoryAPI;
import dev.reislucaz.catalogo.infrastructure.category.models.CategoryApiResponse;
import dev.reislucaz.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import dev.reislucaz.catalogo.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    private final GetCategoryUseCase getCategoryUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase, final GetCategoryUseCase getCategoryUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryUseCase = Objects.requireNonNull(getCategoryUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryApiInput input) {
        CreateCategoryCommand aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<NotificationHandler, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output -> ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String dir) {
        return null;
    }

    @Override
    public CategoryApiResponse getById(String anId) {
        return CategoryApiPresenter.present(
                this.getCategoryUseCase.execute(GetCategoryCommand.with(anId))
        );
    }
}
