'use client'

import { useMemo } from 'react'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useFilterOptions } from '@/hooks/use-filter-options'
import { useListQuery } from '@/hooks/use-list-query'
import { booksApi } from '@/lib/api/books'
import type { BookFilterOptions } from '@/lib/schemas/book/book.filter.options.schema'
import type { BookList } from '@/lib/schemas/book/book.list.schema'

import { getColumns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function BooksPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<BookList[]>(pathname, [resource], booksApi.getAll)
  const { data: filterOptions } = useFilterOptions<BookFilterOptions>(
    ['books-filter-options'],
    booksApi.getFilterOptions
  )

  const columns = useMemo(
    () =>
      getColumns({
        categories: filterOptions?.categories,
        publishers: filterOptions?.publishers,
        languages: filterOptions?.languages,
      }),
    [filterOptions]
  )

  if (error) {
    console.error(`Failed to fetch ${resource}:`, error)
  }

  return (
    <TableListLayout
      columns={columns}
      data={data}
      resource={resource}
      title={title}
      description="Admínistralos sin complicaciones."
      pathname={pathname}
    />
  )
}
