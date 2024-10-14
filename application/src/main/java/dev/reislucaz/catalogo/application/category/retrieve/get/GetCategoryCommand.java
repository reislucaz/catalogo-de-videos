package dev.reislucaz.catalogo.application.category.retrieve.get;

public record GetCategoryCommand(
        String id
) {
    public static GetCategoryCommand with(
            final String id
    ){
        return new GetCategoryCommand(id);
    }
}
