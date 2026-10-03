package com.bookstudio.loan.domain.model;

/**
 * What a change to a loan item means for the physical copy, which the copy
 * module owns.
 */
public enum CopyEffect {
    /** The copy leaves the shelf with the reader. */
    LEND,
    /** The copy is back on the shelf and can be lent again. */
    RELEASE,
    /** The copy was lost while on loan. */
    MARK_LOST,
    /** Nothing changes for the copy. */
    NONE
}
