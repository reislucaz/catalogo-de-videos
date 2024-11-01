package dev.reislucaz.catalogo.infrastructure.api;

import dev.reislucaz.catalogo.domain.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "categories")
@Tag(name = "Categories")
public interface CategoryAPI {
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new category")
    ResponseEntity<?> createCategory();

    @GetMapping
    @Operation(summary = "List paginated categories using a search query")
    Pagination<?> listCategories(
        @RequestParam(name = "search", required = false, defaultValue = "")
        final String search,
        @RequestParam(name = "page", required = false, defaultValue = "0")
        final int page,
        @RequestParam(name = "perPage", required = false, defaultValue = "10")
        final int perPage,
        @RequestParam(name = "sort", required = false, defaultValue = "name")
        final String sort,
        @RequestParam(name = "dir", required = false, defaultValue = "asc")
        final String dir
    );
}
