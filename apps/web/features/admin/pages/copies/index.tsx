'use client'

import { useMemo } from 'react'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useFilterOptions } from '@/hooks/use-filter-options'
import { useListQuery } from '@/hooks/use-list-query'
import { copiesApi } from '@/lib/api/copies'
import type { CopiesFilterOptions } from '@/lib/schemas/copy/copy.filter.options.schema'
import type { CopyList } from '@/lib/schemas/copy/copy.list.schema'

import { getColumns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function CopiesPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<CopyList[]>(pathname, [resource], copiesApi.getAll)
  const { data: filterOptions } = useFilterOptions<CopiesFilterOptions>(
    ['copies-filter-options'],
    copiesApi.getFilterOptions
  )

  const columns = useMemo(
    () =>
      getColumns({
        books: filterOptions?.books,
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
      description="Controla tu inventario fácilmente."
      pathname={pathname}
    />
  )
}
