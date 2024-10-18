package dev.reislucaz.catalogo.application.category.retrieve.list;

import dev.reislucaz.catalogo.application.UseCase;
import dev.reislucaz.catalogo.domain.pagination.Pagination;
import dev.reislucaz.catalogo.domain.pagination.SearchQuery;

public abstract class ListCategoryUseCase extends UseCase<SearchQuery, Pagination<ListCategoryOutput>> {
}
