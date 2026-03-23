'use client'

import { useMemo } from 'react'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useFilterOptions } from '@/hooks/use-filter-options'
import { useListQuery } from '@/hooks/use-list-query'
import { publishersApi } from '@/lib/api/publishers'
import type { PublisherFilterOptions } from '@/lib/schemas/publisher/publisher.filter.options.schema'
import type { PublisherList } from '@/lib/schemas/publisher/publisher.list.schema'

import { getColumns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function PublishersPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<PublisherList[]>(pathname, [resource], publishersApi.getAll)
  const { data: filterOptions } = useFilterOptions<PublisherFilterOptions>(
    ['publishers-filter-options'],
    publishersApi.getFilterOptions
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
      description="Información siempre lista."
      pathname={pathname}
    />
  )
}
