package dev.reislucaz.catalogo.infrastructure.category;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import dev.reislucaz.catalogo.domain.category.CategoryID;
import dev.reislucaz.catalogo.domain.pagination.Pagination;
import dev.reislucaz.catalogo.domain.pagination.SearchQuery;
import dev.reislucaz.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import dev.reislucaz.catalogo.infrastructure.category.persistence.CategoryRepository;
import dev.reislucaz.catalogo.infrastructure.utils.SpecificationUtils;
import static dev.reislucaz.catalogo.infrastructure.utils.SpecificationUtils.like;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Objects;
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
                .map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Category> findAll(SearchQuery aQuery) {
        final var page = PageRequest.of(aQuery.page(), aQuery.perPage(), Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort()));

        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> str.isBlank())
                .map(str ->
                        SpecificationUtils.<CategoryJpaEntity>like("name", str)
                        .or(like("description", str)))
                .orElse(null);

        final var pageResult = this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<Category>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAggregate).toList()
        );
    }
}
