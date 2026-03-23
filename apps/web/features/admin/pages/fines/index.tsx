'use client'

import { useMemo } from 'react'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useFilterOptions } from '@/hooks/use-filter-options'
import { useListQuery } from '@/hooks/use-list-query'
import { finesApi } from '@/lib/api/fines'
import type { FineFilterOptions } from '@/lib/schemas/fine/fine.filter.options.schema'
import type { FineList } from '@/lib/schemas/fine/fine.list.schema'

import { getColumns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function FinesPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<FineList[]>(pathname, [resource], finesApi.getAll)
  const { data: filterOptions } = useFilterOptions<FineFilterOptions>(
    ['fines-filter-options'],
    finesApi.getFilterOptions
  )

  const columns = useMemo(
    () =>
      getColumns({
        loans: filterOptions?.loans,
        copies: filterOptions?.copies,
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
      description="Control de sanciones."
      pathname={pathname}
    />
  )
}
