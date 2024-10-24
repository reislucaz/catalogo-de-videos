package dev.reislucaz.catalogo.infrastructure.category;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import dev.reislucaz.catalogo.domain.category.CategoryID;
import dev.reislucaz.catalogo.domain.pagination.Pagination;
import dev.reislucaz.catalogo.domain.pagination.SearchQuery;
import dev.reislucaz.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {
    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category aCategory) {
        return null;
    }

    @Override
    public void deleteById(CategoryID anId) {

    }

    @Override
    public Category update(Category aCategory) {
        return null;
    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return Optional.empty();
    }

    @Override
    public Pagination<Category> findAll(SearchQuery aQuery) {
        return null;
    }
}
