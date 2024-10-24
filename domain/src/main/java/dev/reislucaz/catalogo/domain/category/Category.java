package dev.reislucaz.catalogo.domain.category;

import dev.reislucaz.catalogo.domain.AggregateRoot;
import dev.reislucaz.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Optional;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant anUpdateDate,
            final Instant aDeleteDate
    ) {
        super(anId);

        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = aCreationDate;
        this.updatedAt = anUpdateDate;
        this.deletedAt = aDeleteDate;
    }

    public static Category newCategory(final String aName, final String aDescription, final boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;

        return new Category(id, aName, aDescription, isActive, now, now, deletedAt);
    }

    public static Category with(
            final CategoryID from,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt) {
        return new Category(
                from,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }


    @Override
    public void validate(ValidationHandler aHandler) {
        new CategoryValidator(this, aHandler).validate();
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();

        return this;
    }

    public Category deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();

        return this;
    }

    public Category update(
            final String aName,
            final String aDescription,
            final boolean isActive
    ) {
        this.name = aName;
        this.description = aDescription;

        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(
            final Optional<String> aName,
            final Optional<String> aDescription,
            final Optional<Boolean> isActive
    ) {
        aName.ifPresent(value -> this.name = value);
        aDescription.ifPresent(value -> this.description = value);

        if (isActive.isPresent()) {
            if (isActive.get()) {
                activate();
            } else {
                deactivate();
            }
        }

        this.updatedAt = Instant.now();
        return this;
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}