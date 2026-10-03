package com.bookstudio.copy.domain.model;

import com.bookstudio.copy.domain.model.type.CopyCondition;
import com.bookstudio.copy.CopyStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import com.bookstudio.shared.code.CodeSeries;

@Entity
@Table(name = "copies")
@Data
public class Copy {
    public static final CodeSeries CODE_SERIES = new CodeSeries("EJE");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    @Column(name = "book_id", nullable = false, updatable = false)
    private Long bookId;

    @Column(name = "shelf_id", nullable = false)
    private Long shelfId;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    private CopyStatus status;

    @Enumerated(EnumType.STRING)
    private CopyCondition condition;
}
