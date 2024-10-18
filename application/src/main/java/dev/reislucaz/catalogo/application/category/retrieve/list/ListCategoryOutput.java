package dev.reislucaz.catalogo.application.category.retrieve.list;

import dev.reislucaz.catalogo.domain.category.Category;

import java.time.Instant;

public record ListCategoryOutput(
        String id,
        String name,
        String description,
        Boolean is_active,
        Instant created_at,
        Instant updated_at,
        Instant deleted_at
) {
    public static ListCategoryOutput from(final Category aCategory) {
        return new ListCategoryOutput(
                aCategory.getId().getValue(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeletedAt());
    }
}
