package dev.reislucaz.catalogo.application;

import dev.reislucaz.catalogo.domain.category.Category;

public class UseCase {
    public Category execute() {
        return new Category();
    }
}