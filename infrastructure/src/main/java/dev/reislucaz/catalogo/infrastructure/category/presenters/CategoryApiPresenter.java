package dev.reislucaz.catalogo.infrastructure.category.presenters;

import dev.reislucaz.catalogo.application.category.retrieve.get.GetCategoryOutput;
import dev.reislucaz.catalogo.infrastructure.category.models.CategoryApiResponse;

public interface CategoryApiPresenter {
    static CategoryApiResponse present(final GetCategoryOutput output){
        return new CategoryApiResponse(
                output.id(),
                output.name(),
                output.description(),
                output.is_active(),
                output.created_at(),
                output.updated_at(),
                output.deleted_at()
        );
    }
}
