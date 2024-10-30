package dev.reislucaz.catalogo.infrastructure.category;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import dev.reislucaz.catalogo.domain.category.CategoryID;
import dev.reislucaz.catalogo.domain.pagination.Pagination;
import dev.reislucaz.catalogo.domain.pagination.SearchQuery;
import dev.reislucaz.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import dev.reislucaz.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {
    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    private Category save(Category aCategory) {
        return repository.save(CategoryJpaEntity.from(aCategory)).toAggregate();
    }

    @Override
    public Category create(Category aCategory) {
        return save(aCategory);
    }

    @Override
    public void deleteById(CategoryID anId) {
        final String anIdValue = anId.getValue();

        if (this.repository.existsById(anIdValue)) {
            this.repository.deleteById(anIdValue);
        }
    }

    @Override
    public Category update(Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return repository.findById(anId.getValue())
                .map(v -> Category.from(v.toAggregate()));
    }

    @Override
    public Pagination<Category> findAll(SearchQuery aQuery) {
        return null;
    }
}
