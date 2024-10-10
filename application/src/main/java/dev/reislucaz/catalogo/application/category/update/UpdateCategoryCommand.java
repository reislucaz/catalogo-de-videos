package dev.reislucaz.catalogo.application.category.update;

import java.util.Optional;

public record UpdateCategoryCommand(
        String id,
        Optional<String> name,
        Optional<String> description,
        Optional<Boolean> isActive
) {
    public static UpdateCategoryCommand with(
            final String id,
            final String aName,
            final String aDescription,
            final Boolean isActive
    ){
        return new UpdateCategoryCommand(id, Optional.ofNullable(aName), Optional.ofNullable(aDescription), Optional.ofNullable(isActive));
    }
}
