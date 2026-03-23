# Architecture and Code Conventions Guide

This guide documents all patterns, conventions, and standards used in this API. Any AI or developer working on this project must follow these rules strictly.

---

## 📁 Package Structure (Layered Architecture)

Each module follows a well-defined 4-layer structure:

```
com.bookstudio.{module}/
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

## 🔗 Dependent Entities (No Separate Module)

### Rule
When an entity **depends entirely on another** and has no meaning on its own, it does **NOT** get its own module folder. Instead, it lives inside the parent module.

### When to Apply
- **Intermediate tables** for many-to-many relations (e.g., `BookAuthor`, `BookGenre`)
- **Child entities** that only exist as part of a parent (e.g., `PaymentFine`, `LoanItem`)
- **Composite IDs** for intermediate tables (e.g., `BookAuthorId`, `LoanItemId`)

### Location Pattern

```
com.bookstudio.{parent}/
├── application/
│   └── dto/
│       ├── request/
│       │   ├── Create{Parent}Request.java
│       │   ├── Update{Parent}Request.java
│       │   ├── Create{Parent}{Child}Request.java   # If child needs its own request DTO
│       │   └── Update{Parent}{Child}Request.java   # If child needs its own request DTO
│       └── response/
│           ├── {Parent}ListResponse.java
│           └── {Parent}DetailResponse.java
│               └── {Child}Item (nested record)     # Child DTOs are nested records
├── domain/
│   └── model/
│       ├── {Parent}.java           # Main entity
│       ├── {Parent}{Child}.java    # Dependent entity
│       └── {Parent}{Child}Id.java  # Composite ID (if needed)
└── infrastructure/
    └── repository/
        ├── {Parent}Repository.java
        └── {Parent}{Child}Repository.java  # Repository for dependent entity
```

### Real Examples

#### Book Module (with many-to-many relations)
```
com.bookstudio.book/
├── application/
│   └── dto/
│       ├── request/
│       │   ├── CreateBookRequest.java     # Contains List<Long> authorIds, genreIds
│       │   └── UpdateBookRequest.java
│       └── response/
│           ├── BookListResponse.java
│           └── BookDetailResponse.java
│               ├── AuthorItem (nested record)
│               └── GenreItem (nested record)
├── domain/
│   └── model/
│       ├── Book.java
│       ├── BookAuthor.java      # Intermediate table Book <-> Author
│       ├── BookAuthorId.java    # Composite ID
│       ├── BookGenre.java       # Intermediate table Book <-> Genre
│       └── BookGenreId.java     # Composite ID
└── infrastructure/
    └── repository/
        ├── BookRepository.java
        ├── BookAuthorRepository.java
        └── BookGenreRepository.java
```

#### Loan Module (with child entity that has its own request DTO)
```
com.bookstudio.loan/
├── application/
│   └── dto/
│       ├── request/
│       │   ├── CreateLoanRequest.java          # Contains List<CreateLoanItemRequest>
│       │   ├── CreateLoanItemRequest.java      # Separate file for item request
│       │   ├── UpdateLoanRequest.java          # Contains List<UpdateLoanItemRequest>
│       │   └── UpdateLoanItemRequest.java      # Separate file for item request
│       └── response/
│           ├── LoanListResponse.java
│           └── LoanDetailResponse.java
│               └── LoanItem (nested record)    # Response uses nested record
├── domain/
│   └── model/
│       ├── Loan.java
│       ├── LoanItem.java        # Individual items in a loan
│       └── LoanItemId.java      # Composite ID
└── infrastructure/
    └── repository/
        ├── LoanRepository.java
        └── LoanItemRepository.java
```

#### Payment Module (with simple child reference)
```
com.bookstudio.payment/
├── application/
│   └── dto/
│       ├── request/
│       │   ├── CreatePaymentRequest.java   # Contains List<Long> fineIds (just IDs)
│       │   └── UpdatePaymentRequest.java
│       └── response/
│           ├── PaymentListResponse.java
│           └── PaymentDetailResponse.java
│               └── FineItem (nested record)
├── domain/
│   └── model/
│       ├── Payment.java
│       ├── PaymentFine.java     # Payment detail for fines
│       └── PaymentFineId.java   # Composite ID
└── infrastructure/
    └── repository/
        ├── PaymentRepository.java
        └── PaymentFineRepository.java
```

### Key Points

1. **No separate module folder** for dependent entities
2. **Models**: `{Parent}{Child}.java` in parent's `domain/model/`
3. **Composite IDs**: `{Parent}{Child}Id.java` for intermediate tables
4. **Repositories**: `{Parent}{Child}Repository.java` in parent's `infrastructure/repository/`
5. **Request DTOs**: Two options depending on complexity:
   - **Simple**: Just `List<Long> {child}Ids` in parent request (e.g., `authorIds`, `fineIds`)
   - **Complex**: Separate `Create{Parent}{Child}Request.java` file (e.g., `CreateLoanItemRequest`)
6. **Response DTOs**: Always use **nested records** inside parent's response (e.g., `LoanDetailResponse.LoanItem`)

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
List<EntityListResponse> findList();                    // Full listing
List<OptionResponse> findForOptions();                   // For selects/filters
Optional<EntityDetailResponse> findDetailById(Long id);  // Detail by ID
Optional<Entity> findById(Long id);                      // Inherited from JpaRepository
Entity save(Entity entity);                              // Inherited from JpaRepository

// For Many-to-Many relations (in intermediate table repository)
List<EntityDetailResponse.ItemType> find{Items}By{Entity}Id(Long id);
void deleteAllBy{Entity}(Entity entity);
```

### Real Examples

#### BookAuthorRepository (Intermediate Table)
```java
List<BookDetailResponse.AuthorItem> findAuthorItemsByBookId(Long id);
void deleteAllByBook(Book book);
```

#### LoanItemRepository (Intermediate Table)
```java
List<LoanDetailResponse.LoanItem> findLoanItemsByLoanId(Long id);
void deleteAllByLoan(Loan loan);
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

### Query for Items in Intermediate Table

```java
// In intermediate table repository (e.g.: BookAuthorRepository)
@Query("""
    SELECT 
        a.id AS id,
        a.name AS name
    FROM BookAuthor ba
    JOIN ba.author a
    WHERE ba.book.id = :id
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
            bookAuthorRepository.findAuthorItemsByBookId(id),
            bookGenreRepository.findGenreItemsByBookId(id));
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
    JOIN e.related r
    WHERE e.id = :id
    ORDER BY e.id DESC
""")
```

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
    LEFT JOIN b.copies c
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
    private final RelatedRepository relatedRepository;
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
            relatedRepository.findForOptions());
}

// GET DETAIL BY ID - Uses orElseThrow for 404
public EntityDetailResponse getDetailById(Long id) {
    return entityRepository.findDetailById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found with ID: " + id));
}

// CREATE - Mark @Transactional for write
@Transactional
public EntityListResponse create(CreateEntityRequest request) {
    Entity entity = new Entity();
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

For create/update, use a private method that converts entity to DTO:

```java
private EntityListResponse toListResponse(Entity entity) {
    return new EntityListResponse(
            entity.getId(),
            entity.getName(),

            entity.getRelated().getId(),
            entity.getRelated().getName(),

            entity.getStatus());
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();
}
```

### Intermediate Table (Many-to-Many)

```java
@Entity
@Table(name = "entity_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EntityItem {
    @EmbeddedId
    private EntityItemId id;

    @ManyToOne
    @MapsId("entityId")
    @JoinColumn(name = "entity_id")
    private Entity entity;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityItemId implements Serializable {
    private Long entityId;
    private Long itemId;
}
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
| **Many-to-Many** | Use `withItems()` pattern |
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

**Version**: 1.0  
**Last updated**: February 2026
