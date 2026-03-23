'use client'

import { useMemo } from 'react'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useFilterOptions } from '@/hooks/use-filter-options'
import { useListQuery } from '@/hooks/use-list-query'
import { workersApi } from '@/lib/api/workers'
import type { WorkerFilterOptions } from '@/lib/schemas/worker/worker.filter.options.schema'
import type { WorkerList } from '@/lib/schemas/worker/worker.list.schema'

import { getColumns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function WorkersPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<WorkerList[]>(pathname, [resource], workersApi.getAll)
  const { data: filterOptions } = useFilterOptions<WorkerFilterOptions>(
    ['workers-filter-options'],
    workersApi.getFilterOptions
  )

  const columns = useMemo(
    () =>
      getColumns({
        roles: filterOptions?.roles,
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
      description="Administra tu equipo."
      pathname={pathname}
    />
  )
}
