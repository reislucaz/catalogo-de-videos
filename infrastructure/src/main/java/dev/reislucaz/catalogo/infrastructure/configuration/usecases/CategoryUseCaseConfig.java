package dev.reislucaz.catalogo.infrastructure.configuration.usecases;

import dev.reislucaz.catalogo.application.category.create.CreateCategoryUseCase;
import dev.reislucaz.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import dev.reislucaz.catalogo.application.category.retrieve.get.DefaultGetCategoryUseCase;
import dev.reislucaz.catalogo.application.category.retrieve.get.GetCategoryUseCase;
import dev.reislucaz.catalogo.application.category.retrieve.list.DefaultListCategoryUseCase;
import dev.reislucaz.catalogo.application.category.retrieve.list.ListCategoryUseCase;
import dev.reislucaz.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import dev.reislucaz.catalogo.application.category.update.UpdateCategoryUseCase;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {
    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public ListCategoryUseCase listCategoryUseCase() {
        return new DefaultListCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryUseCase getCategoryUseCase() {
        return new DefaultGetCategoryUseCase(categoryGateway);
    }
}
