package dev.reislucaz.catalogo.application;

public abstract class UnitUseCase<Input> {
    public abstract void execute(Input anInput);
}
