package dev.reislucaz.catalogo.domain.category;

import dev.reislucaz.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {
    Category create(Category aCategory);

    void deleteById(CategoryID anId);

    Category update(Category aCategory);

    Optional<Category> findById(CategoryID anId);

    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
