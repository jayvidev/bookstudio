# Architecture and Code Conventions Guide

This guide documents all patterns, conventions, and standards used in this API. Any AI or developer working on this project must follow these rules strictly.

---

## 🧱 Module Boundaries (Spring Modulith)

Every top-level package is an application module, and `ModularityTests` runs
`ApplicationModules.verify()` on every build. Rationale and trade-offs:
[ADR 0001](adr/0001-modular-monolith.md).

| Rule | Example |
|------|---------|
| Only the module's **root package** is public | `com.bookstudio.reader.ReaderApi` |
| Other modules talk to it through that API, never its repositories or entities | `readerApi.requireExists(id)` |
| Entities reference other modules' aggregates **by id** | `private Long readerId;` |
| Cross-module writes go through intention-revealing methods | `fineApi.markPaid(fineIds)` |
| Read projections may join other modules' entities by id | `JOIN Reader r ON r.id = l.readerId` |
| Enums shown in other modules' responses live in the module root | `com.bookstudio.copy.CopyStatus` |
| `shared` is an open module (shared kernel) | `ApiSuccess`, `CodeGenerator` |
| No dependency cycles | |

### Public API

```java
package com.bookstudio.reader;

public interface ReaderApi {
    void requireExists(Long id);           // throws ResourceNotFoundException
    List<OptionResponse> getOptions();
}
```

The module's service implements it (`ReaderService implements ReaderApi`), or a
package-private service does when the module has no controller
(`NationalityService`, `GenreService`, `LanguageService`).

---

## 📁 Package Structure (Layered Architecture)

Each module follows a well-defined 4-layer structure:

```
com.bookstudio.{module}/
├── {Module}Api.java      # Public API used by other modules (if any)
├── application/          # Business logic
│   ├── {Module}Service.java
│   └── dto/
│       ├── request/      # Input DTOs
│       │   ├── Create{Module}Request.java
│       │   └── Update{Module}Request.java
│       └── response/     # Output DTOs
│           ├── {Module}ListResponse.java
│           ├── {Module}DetailResponse.java
│           ├── {Module}FilterOptionsResponse.java
│           └── {Module}SelectOptionsResponse.java
├── domain/               # Domain entities
│   └── model/
│       ├── {Module}.java
│       └── type/         # Domain enums
│           └── {Module}Status.java
├── infrastructure/       # Data access
│   └── repository/
│       └── {Module}Repository.java
└── presentation/         # REST controllers
    └── {Module}Controller.java
```

---

## 🔗 Relations: Child Entities vs. References

### Child entities (same aggregate)
An entity that only exists as part of a parent lives in the parent module and
keeps a real JPA association: `LoanItem` (with `LoanItemId`) inside `loan`,
`Shelf` inside `location`.

### References to other modules
Store the id, never the other module's entity:

```java
@Column(name = "reader_id", nullable = false)
private Long readerId;
```

### Many-to-many to another module
No intermediate entity or repository. The owning entity keeps an element
collection of ids mapped to the existing join table:

```java
@ElementCollection
@CollectionTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"))
@Column(name = "author_id")
private Set<Long> authorIds = new HashSet<>();

public void replaceAuthors(Collection<Long> ids) {
    authorIds.clear();
    authorIds.addAll(ids);
}
```

Used by `Book.authorIds`, `Book.genreIds`, `Publisher.genreIds` and
`Payment.fineIds`.

### Request DTOs
- **Simple**: `List<Long> {child}Ids` in the parent request (`authorIds`, `fineIds`).
- **Complex**: a separate `Create{Parent}{Child}Request` (`CreateLoanItemRequest`).
- A request never uses another module's types; it declares its own record
  (`fine`'s `LoanItemRef` instead of `loan`'s `LoanItemId`).

### Response DTOs
Child items are **nested records** inside the parent's response
(`LoanDetailResponse.LoanItem`, `BookDetailResponse.AuthorItem`).

---

## 🏷️ Method Naming Conventions

### Comparison Table by Layer

| Operation | Controller | Service | Repository |
|-----------|------------|---------|------------|
| **List all** | `list()` | `getList()` | `findList()` |
| **Filter options** | `filterOptions()` | `getFilterOptions()` | `findForOptions()` |
| **Select options** | `selectOptions()` | `getSelectOptions()` | `findForOptions()` |
| **Get by ID** | `get(@PathVariable Long id)` | `getDetailById(Long id)` | `findDetailById(Long id)` |
| **Create** | `create(@RequestBody Request)` | `create(Request request)` | `save(Entity entity)` |
| **Update** | `update(@PathVariable Long id, @RequestBody Request)` | `update(Long id, Request request)` | `save(Entity entity)` |
| **M:N relation items** | - | - | `find{Items}By{Entity}Id(Long id)` |

### Patterns by Layer

#### Controller
```java
public ResponseEntity<ApiSuccess<List<EntityListResponse>>> list() { }
public ResponseEntity<ApiSuccess<EntityFilterOptionsResponse>> filterOptions() { }
public ResponseEntity<ApiSuccess<EntitySelectOptionsResponse>> selectOptions() { }
public ResponseEntity<ApiSuccess<EntityDetailResponse>> get(@PathVariable Long id) { }
public ResponseEntity<ApiSuccess<EntityListResponse>> create(@RequestBody CreateEntityRequest request) { }
public ResponseEntity<ApiSuccess<EntityListResponse>> update(@PathVariable Long id, @RequestBody UpdateEntityRequest request) { }
```

#### Service
```java
public List<EntityListResponse> getList() { }
public EntityFilterOptionsResponse getFilterOptions() { }
public EntitySelectOptionsResponse getSelectOptions() { }
public EntityDetailResponse getDetailById(Long id) { }
public EntityListResponse create(CreateEntityRequest request) { }
public EntityListResponse update(Long id, UpdateEntityRequest request) { }
private EntityListResponse toListResponse(Entity entity) { }  // Private helper method
```

#### Repository
```java
List<EntityListResponse> findList();                       // Full listing
Optional<EntityListResponse> findListItemById(Long id);     // Same projection, one row (after writes)
List<OptionResponse> findForOptions();                      // For selects/filters
Optional<EntityDetailResponse> findDetailById(Long id);     // Detail by ID
Optional<Entity> findById(Long id);                         // Inherited from JpaRepository
Entity save(Entity entity);                                 // Inherited from JpaRepository

// Items of a many-to-many or child relation
List<EntityDetailResponse.ItemType> find{Items}By{Entity}Id(Long id);
```

### Real Examples

```java
// BookRepository: items of the book_authors element collection
List<BookDetailResponse.AuthorItem> findAuthorItemsByBookId(Long id);

// LoanItemRepository: child entities of the same aggregate
List<LoanDetailResponse.LoanItem> findLoanItemsByLoanId(Long id);
```

---

## 📝 DTOs - Java Records

### Main Rule
**ALWAYS use `record` for all DTOs**, both request and response.

### Indentation Format
- **4 spaces** indentation for each field
- Closing parenthesis `)` goes on its **own line**
- **Blank line** to separate logical groups of fields

### Base Example

```java
public record EntityListResponse(
    Long id,
    String name,
    String description,

    @JsonIgnore Long relatedEntityId,
    @JsonIgnore String relatedEntityName,

    Status status
) {

    @JsonGetter("relatedEntity")
    public RelatedEntity getRelatedEntity() {
        return new RelatedEntity(relatedEntityId, relatedEntityName);
    }

    public record RelatedEntity(
        Long id,
        String name
    ) {}
}
```

---

## 🔄 Pattern for Nested Objects in Response DTOs

### Problem
JPQL with projections cannot create nested objects directly.

### Solution
1. Project flat fields with `@JsonIgnore`
2. Create method with `@JsonGetter` that builds the nested object
3. Define the nested record inside the main DTO

### Standard Implementation

```java
@JsonPropertyOrder({ "id", "name", "category", "status" })
public record ProductListResponse(
    Long id,
    String name,

    @JsonIgnore Long categoryId,
    @JsonIgnore String categoryName,

    Status status
) {

    @JsonGetter("category")
    public Category getCategory() {
        return new Category(categoryId, categoryName);
    }

    public record Category(
        Long id,
        String name
    ) {}
}
```

### Naming Convention
- Flat fields: `{entity}Id`, `{entity}Name`, `{entity}Code`, etc.
- Getter method: `get{Entity}()`
- Nested record: `{Entity}` (no prefix)
- `@JsonGetter("{entity}")` in lowercase/camelCase for final JSON

---

## 🔗 Many-to-Many Relations - `withItems()` Pattern

### Problem
Many-to-many relationships cannot be projected in a single JPQL query.

### Solution - `with{Items}` Constructor

The **DetailResponse** includes:
1. A field of type `List<ItemType>` with `NULL` value in the main query
2. A `with{Items}()` method that returns a new instance with the populated list

### DetailResponse Structure

```java
@JsonPropertyOrder({ "id", "name", "description", "items" })
public record EntityDetailResponse(
    Long id,
    String name,
    String description,

    @JsonIgnore Long relatedId,
    @JsonIgnore String relatedName,

    List<Item> items  // Will be NULL from query
) {

    // "with" constructor to add items afterwards
    public EntityDetailResponse withItems(List<Item> items) {
        return new EntityDetailResponse(
            id, name, description, relatedId, relatedName, items
        );
    }

    // For multiple lists (e.g.: authors and genres)
    public EntityDetailResponse withAuthorsAndGenres(
            List<AuthorItem> authors, 
            List<GenreItem> genres) {
        return new EntityDetailResponse(
            id, name, description, relatedId, relatedName, 
            authors, genres
        );
    }

    @JsonGetter("related")
    public Related getRelated() {
        return new Related(relatedId, relatedName);
    }

    public record Related(
        Long id,
        String name
    ) {}

    public record Item(
        Long id,
        String name
    ) {}
}
```

### Query for Items of an Element Collection

```java
// In the owning repository (e.g.: BookRepository)
@Query("""
    SELECT
        a.id AS id,
        a.name AS name
    FROM Book b
    JOIN b.authorIds authorId
    JOIN Author a ON a.id = authorId
    WHERE b.id = :id
    ORDER BY a.id
""")
List<BookDetailResponse.AuthorItem> findAuthorItemsByBookId(Long id);
```

### Usage in Service

```java
public EntityDetailResponse getDetailById(Long id) {
    EntityDetailResponse base = repository.findDetailById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found with ID: " + id));

    return base.withItems(itemRepository.findItemsByEntityId(id));
}

// For multiple lists:
public BookDetailResponse getDetailById(Long id) {
    BookDetailResponse base = bookRepository.findDetailById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

    return base.withAuthorsAndGenres(
            bookRepository.findAuthorItemsByBookId(id),
            bookRepository.findGenreItemsByBookId(id));
}
```

---

## 🗃️ Repositories - Optimization with @Query and Projections

### Main Rule
**ALWAYS use `@Query` with `AS` projections for listings and details**.
Never load the full entity and then map to DTO.

### Query Format

```java
@Query("""
    SELECT 
        e.id AS id,
        e.name AS name,
        e.code AS code,

        r.id AS relatedId,
        r.name AS relatedName,

        e.status AS status
    FROM Entity e
    JOIN Related r ON r.id = e.relatedId
    WHERE e.id = :id
    ORDER BY e.id DESC
""")
```

Join other modules' entities by id (`JOIN Related r ON r.id = e.relatedId`);
navigate associations (`JOIN e.items i`) only inside the same aggregate.

### Sharing the List Projection

The list query is declared once as a constant and reused for the single-row
variant that services use after a write:

```java
String LIST_SELECT = """
    SELECT
        a.id AS id,
        a.name AS name,
        n.name AS nationalityName
    FROM Author a
    JOIN Nationality n ON n.id = a.nationalityId
    """;

@Query(LIST_SELECT + "ORDER BY a.id DESC")
List<AuthorListResponse> findList();

@Query(LIST_SELECT + "WHERE a.id = :id")
Optional<AuthorListResponse> findListItemById(Long id);
```

Queries with `GROUP BY` add a `LIST_GROUP_BY` constant and put the `WHERE`
before it.

### Query Conventions

1. **Use text blocks** (`"""`) for multiline queries
2. **One field per line** with trailing comma
3. **Blank line** between logical groups of fields
4. **Consistent aliases**: use `AS fieldName` that matches exactly with DTO field
5. **Field order** same as in the record declaration

### Repository Method Types

```java
public interface EntityRepository extends JpaRepository<Entity, Long> {

    // 1. Full listing - returns List<ListResponse>
    @Query("""
        SELECT 
            e.id AS id,
            e.name AS name,
            e.status AS status
        FROM Entity e
        ORDER BY e.id DESC
    """)
    List<EntityListResponse> findList();

    // 2. Options for selects/filters - returns List<OptionResponse>
    @Query("""
        SELECT 
            e.id AS value,
            e.name AS label
        FROM Entity e
        WHERE e.status = 'ACTIVO'
        ORDER BY e.name ASC
    """)
    List<OptionResponse> findForOptions();

    // 3. Detail by ID - returns Optional<DetailResponse>
    @Query("""
        SELECT 
            e.id AS id,
            e.name AS name,
            e.description AS description,
            NULL AS items
        FROM Entity e
        WHERE e.id = :id
    """)
    Optional<EntityDetailResponse> findDetailById(Long id);
}
```

### Calculations and Aggregations in Query

```java
@Query("""
    SELECT 
        b.id AS id,
        b.title AS title,

        COALESCE(SUM(CASE WHEN c.status <> 'DISPONIBLE' THEN 1 ELSE 0 END), 0) AS copiesLoaned,
        COALESCE(SUM(CASE WHEN c.status = 'DISPONIBLE' THEN 1 ELSE 0 END), 0) AS copiesAvailable,

        b.status AS status
    FROM Book b
    LEFT JOIN Copy c ON c.bookId = b.id
    GROUP BY b.id, b.title, b.status
    ORDER BY b.id DESC
""")
List<BookListResponse> findList();
```

---

## ⚡ Services - Structure and Patterns

### Class Annotations

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // Default read-only
@Validated
public class EntityService {
    private final EntityRepository entityRepository;
    private final RelatedApi relatedApi;          // another module: its API, never its repository
```

### Standard Methods

```java
// GET LIST - Always delegates directly to repository
public List<EntityListResponse> getList() {
    return entityRepository.findList();
}

// GET FILTER OPTIONS - Uses OptionResponse
public EntityFilterOptionsResponse getFilterOptions() {
    return new EntityFilterOptionsResponse(
            relatedApi.getOptions());
}

// GET DETAIL BY ID - Uses orElseThrow for 404
public EntityDetailResponse getDetailById(Long id) {
    return entityRepository.findDetailById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found with ID: " + id));
}

// CREATE - Mark @Transactional for write
@Transactional
public EntityListResponse create(CreateEntityRequest request) {
    relatedApi.requireExists(request.relatedId());

    Entity entity = new Entity();
    entity.setRelatedId(request.relatedId());
    // ... set fields
    Entity saved = entityRepository.save(entity);
    return toListResponse(saved);
}

// UPDATE - Mark @Transactional for write
@Transactional
public EntityListResponse update(Long id, UpdateEntityRequest request) {
    Entity entity = entityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found with ID: " + id));
    // ... update fields
    Entity updated = entityRepository.save(entity);
    return toListResponse(updated);
}
```

### Private `toListResponse` Method

After create/update, re-read the same projection the list endpoint uses, so
each response has a single source of truth (JPQL auto-flushes pending writes):

```java
private EntityListResponse toListResponse(Entity entity) {
    return entityRepository.findListItemById(entity.getId()).orElseThrow();
}
```

---

## 📥 Request DTOs

### Format and Validations

```java
public record CreateEntityRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name,

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    String description,

    @NotNull(message = "Category ID is required")
    @Min(value = 1, message = "Category ID must be at least 1")
    Long categoryId,

    @NotBlank(message = "Status is required")
    @ValidEnum(enumClass = Status.class, message = "Invalid status")
    String status
) {}
```

### Request with Items (Relations)

```java
public record CreateEntityRequest(
    @NotNull(message = "Parent ID is required")
    @Min(value = 1, message = "Parent ID must be at least 1")
    Long parentId,

    String observation,

    @NotNull(message = "Items are required")
    @NotEmpty(message = "Items cannot be empty")
    @NoNullElements(message = "Items cannot contain null elements")
    @Valid
    List<CreateEntityItemRequest> items
) {}

public record CreateEntityItemRequest(
    @NotNull(message = "Related ID is required")
    @Min(value = 1, message = "Related ID must be at least 1")
    Long relatedId,

    @NotNull(message = "Date is required")
    @Future(message = "Date must be in the future")
    LocalDate date
) {}
```

---

## 🎯 Special Response DTOs

### OptionResponse (Shared)

```java
// In com.bookstudio.shared.response
public record OptionResponse(
    Long value,
    String label
) {}
```

### FilterOptionsResponse

```java
public record EntityFilterOptionsResponse(
    List<OptionResponse> categories,
    List<OptionResponse> statuses
) {}
```

### SelectOptionsResponse

```java
public record EntitySelectOptionsResponse(
    List<OptionResponse> categories,
    List<OptionResponse> languages,
    List<OptionResponse> publishers
) {}
```

---

## 🌐 Controllers

### Class Annotations

```java
@RestController
@RequestMapping("/entities")
@RequiredArgsConstructor
@Validated
@Tag(name = "Entities", description = "Operations related to entities")
public class EntityController {
    private final EntityService entityService;
```

### Standard Endpoints

```java
// GET /entities
@GetMapping
@Operation(summary = "List all entities")
public ResponseEntity<ApiSuccess<List<EntityListResponse>>> list() {
    List<EntityListResponse> entities = entityService.getList();
    ApiSuccess<List<EntityListResponse>> response = new ApiSuccess<>(
            entities.isEmpty() ? "No entities found" : "Entities listed successfully",
            entities);
    return ResponseEntity.ok(response);
}

// GET /entities/filter-options
@GetMapping("/filter-options")
@Operation(summary = "Get filter options for entities")
public ResponseEntity<ApiSuccess<EntityFilterOptionsResponse>> filterOptions() {
    // ...
}

// GET /entities/{id}
@GetMapping("/{id}")
@Operation(summary = "Get an entity by ID")
public ResponseEntity<ApiSuccess<EntityDetailResponse>> get(
        @PathVariable @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) Long id) {
    EntityDetailResponse entity = entityService.getDetailById(id);
    return ResponseEntity.ok(new ApiSuccess<>("Entity found", entity));
}

// POST /entities
@PostMapping
@Operation(summary = "Create a new entity")
public ResponseEntity<ApiSuccess<EntityListResponse>> create(
        @Valid @RequestBody CreateEntityRequest request) {
    EntityListResponse created = entityService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiSuccess<>("Entity created successfully", created));
}

// PUT /entities/{id}
@PutMapping("/{id}")
@Operation(summary = "Update an entity by ID")
public ResponseEntity<ApiSuccess<EntityListResponse>> update(
        @PathVariable @Min(value = 1, message = ValidationMessages.ID_MIN_VALUE) Long id,
        @Valid @RequestBody UpdateEntityRequest request) {
    EntityListResponse updated = entityService.update(id, request);
    return ResponseEntity.ok(new ApiSuccess<>("Entity updated successfully", updated));
}
```

---

## 🏗️ Domain Entities

### Main Entity

```java
@Entity
@Table(name = "entities")
@Data
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "category_id", nullable = false)   // another module: id only
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();     // child entities of the same aggregate
}
```

### Business Codes

Entities with a human-readable code declare their series and get the code from
`CodeGenerator` before saving ([ADR 0002](adr/0002-business-codes.md)):

```java
public static final CodeSeries CODE_SERIES = new CodeSeries("PRE");   // PRE-2026-00001

@Column(nullable = false, unique = true, updatable = false)
private String code;
```

```java
loan.setCode(codeGenerator.next(Loan.CODE_SERIES, loan.getLoanDate()));
```

---

## 🚨 Exception Handling

### ResourceNotFoundException

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

### Usage

```java
throw new ResourceNotFoundException("Entity not found with ID: " + id);
throw new ResourceNotFoundException("Category not found with ID: " + request.categoryId());
```

---

## 📋 API Response Wrappers

### ApiSuccess<T>

```java
@JsonPropertyOrder({ "success", "message", "data", "timestamp" })
public class ApiSuccess<T> {
    private final boolean success = true;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
```

### ApiError

```java
@JsonPropertyOrder({ "success", "status", "message", "path", "timestamp", "errors" })
public class ApiError {
    private final boolean success = false;
    private final int status;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final List<ApiFieldError> errors;
}
```

---

## ✅ Key Rules Summary

| Aspect | Rule |
|--------|------|
| **DTOs** | Always use `record` |
| **Indentation** | 4 spaces |
| **Queries** | Always with `@Query` and `AS` projections |
| **Listings** | Never load full entity |
| **Module boundaries** | Other modules only through `{Module}Api`; references by id |
| **Many-to-Many** | `@ElementCollection` of ids + `withItems()` pattern |
| **After writes** | Re-read with `findListItemById` |
| **Nested objects** | `@JsonIgnore` + `@JsonGetter` + inner record |
| **Options** | Use shared `OptionResponse(value, label)` |
| **Transactions** | `@Transactional(readOnly = true)` on class, `@Transactional` on write methods |
| **Exceptions** | Throw `ResourceNotFoundException` with clear message |
| **Validations** | In request DTOs with Jakarta Validation annotations |

---

## 🔧 Custom Validators

### @ValidEnum

```java
@Constraint(validatedBy = ValidEnumValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
    String message() default "Invalid value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass();
}
```

### @NoNullElements

To validate that a list does not contain null elements.

---

## 📦 Shared Package

```
com.bookstudio.shared/
├── api/
│   ├── ApiSuccess.java
│   ├── ApiError.java
│   └── ApiFieldError.java
├── exception/
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
├── code/
│   ├── CodeSeries.java
│   └── CodeGenerator.java
├── response/
│   └── OptionResponse.java
├── type/
│   └── Status.java
└── validation/
    ├── ValidEnum.java
    ├── ValidEnumValidator.java
    ├── NoNullElements.java
    ├── NoNullElementsValidator.java
    └── ValidationMessages.java
```

---

**Version**: 2.0  
**Last updated**: October 2026
