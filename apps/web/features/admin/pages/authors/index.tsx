'use client'

import { useMemo } from 'react'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useFilterOptions } from '@/hooks/use-filter-options'
import { useListQuery } from '@/hooks/use-list-query'
import { authorsApi } from '@/lib/api/authors'
import type { AuthorFilterOptions } from '@/lib/schemas/author/author.filter.options.schema'
import type { AuthorList } from '@/lib/schemas/author/author.list.schema'

import { getColumns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function AuthorsPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<AuthorList[]>(pathname, [resource], authorsApi.getAll)
  const { data: filterOptions } = useFilterOptions<AuthorFilterOptions>(
    ['authors-filter-options'],
    authorsApi.getFilterOptions
  )

  const columns = useMemo(
    () =>
      getColumns({
        nationalities: filterOptions?.nationalities,
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
      description="Rápidos, claros y ordenados."
      pathname={pathname}
    />
  )
}
