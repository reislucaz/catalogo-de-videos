package dev.reislucaz.catalogo.application;

public abstract class UseCase<Input, Output> {
    public abstract Output execute(Input anInput);
}