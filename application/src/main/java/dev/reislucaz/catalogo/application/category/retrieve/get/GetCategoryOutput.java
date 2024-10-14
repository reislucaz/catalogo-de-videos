package dev.reislucaz.catalogo.application.category.retrieve.get;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryID;

import java.time.Instant;

public record GetCategoryOutput(
        String id,
        String name,
        String description,
        Boolean is_active,
        Instant created_at,
        Instant updated_at,
        Instant deleted_at
) {
    public static GetCategoryOutput from(final Category aCategory) {
        return new GetCategoryOutput(
                aCategory.getId().getValue(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeletedAt());
    }
}
