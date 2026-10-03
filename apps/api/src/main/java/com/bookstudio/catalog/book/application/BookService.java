package com.bookstudio.catalog.book.application;

import com.bookstudio.catalog.author.AuthorApi;
import com.bookstudio.catalog.BookApi;
import com.bookstudio.catalog.book.application.dto.request.CreateBookRequest;
import com.bookstudio.catalog.book.application.dto.request.UpdateBookRequest;
import com.bookstudio.catalog.book.application.dto.response.BookDetailResponse;
import com.bookstudio.catalog.book.application.dto.response.BookFilterOptionsResponse;
import com.bookstudio.catalog.book.application.dto.response.BookListResponse;
import com.bookstudio.catalog.book.application.dto.response.BookSelectOptionsResponse;
import com.bookstudio.catalog.book.domain.model.Book;
import com.bookstudio.catalog.book.infrastructure.repository.BookRepository;
import com.bookstudio.catalog.category.CategoryApi;
import com.bookstudio.catalog.genre.GenreApi;
import com.bookstudio.catalog.language.LanguageApi;
import com.bookstudio.catalog.publisher.PublisherApi;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.response.OptionResponse;
import com.bookstudio.shared.type.Status;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class BookService implements BookApi {
    private final BookRepository bookRepository;

    private final LanguageApi languageApi;
    private final PublisherApi publisherApi;
    private final CategoryApi categoryApi;
    private final AuthorApi authorApi;
    private final GenreApi genreApi;

    @Override
    public void requireExists(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with ID: " + id);
        }
    }

    @Override
    public List<OptionResponse> getOptions() {
        return bookRepository.findForOptions();
    }

    public List<BookListResponse> getList() {
        return bookRepository.findList();
    }

    public BookFilterOptionsResponse getFilterOptions() {
        return new BookFilterOptionsResponse(
                categoryApi.getOptions(),
                publisherApi.getOptions(),
                languageApi.getOptions());
    }

    public BookSelectOptionsResponse getSelectOptions() {
        return new BookSelectOptionsResponse(
                languageApi.getOptions(),
                publisherApi.getOptions(),
                categoryApi.getOptions());
    }

    public BookDetailResponse getDetailById(Long id) {
        BookDetailResponse base = bookRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        return base.withAuthorsAndGenres(
                bookRepository.findAuthorItemsByBookId(id),
                bookRepository.findGenreItemsByBookId(id));
    }

    @Transactional
    public BookListResponse create(CreateBookRequest request) {
        Book book = new Book();
        languageApi.requireExists(request.languageId());
        publisherApi.requireExists(request.publisherId());
        categoryApi.requireExists(request.categoryId());
        authorApi.requireAllExist(request.authorIds());
        genreApi.requireAllExist(request.genreIds());

        book.setLanguageId(request.languageId());
        book.setPublisherId(request.publisherId());
        book.setCategoryId(request.categoryId());
        book.replaceAuthors(request.authorIds());
        book.replaceGenres(request.genreIds());

        book.setTitle(request.title());
        book.setIsbn(request.isbn());
        book.setEdition(request.edition());
        book.setPages(request.pages());
        book.setDescription(request.description());
        book.setCoverUrl(request.coverUrl());
        book.setReleaseDate(request.releaseDate());
        book.setStatus(Status.valueOf(request.status()));

        Book saved = bookRepository.save(book);

        return toListResponse(saved);
    }

    @Transactional
    public BookListResponse update(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        languageApi.requireExists(request.languageId());
        publisherApi.requireExists(request.publisherId());
        categoryApi.requireExists(request.categoryId());
        authorApi.requireAllExist(request.authorIds());
        genreApi.requireAllExist(request.genreIds());

        book.setLanguageId(request.languageId());
        book.setPublisherId(request.publisherId());
        book.setCategoryId(request.categoryId());
        book.replaceAuthors(request.authorIds());
        book.replaceGenres(request.genreIds());

        book.setTitle(request.title());
        book.setIsbn(request.isbn());
        book.setEdition(request.edition());
        book.setPages(request.pages());
        book.setDescription(request.description());
        book.setCoverUrl(request.coverUrl());
        book.setReleaseDate(request.releaseDate());
        book.setStatus(Status.valueOf(request.status()));

        Book updated = bookRepository.save(book);

        return toListResponse(updated);
    }

    private BookListResponse toListResponse(Book book) {
        return bookRepository.findListItemById(book.getId()).orElseThrow();
    }
}
