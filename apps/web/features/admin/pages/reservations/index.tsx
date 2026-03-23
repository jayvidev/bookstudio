'use client'

import { useMemo } from 'react'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useFilterOptions } from '@/hooks/use-filter-options'
import { useListQuery } from '@/hooks/use-list-query'
import { reservationsApi } from '@/lib/api/reservations'
import type { ReservationFilterOptions } from '@/lib/schemas/reservation/reservation.filter.options.schema'
import type { ReservationList } from '@/lib/schemas/reservation/reservation.list.schema'

import { getColumns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function ReservationsPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<ReservationList[]>(
    pathname,
    [resource],
    reservationsApi.getAll
  )
  const { data: filterOptions } = useFilterOptions<ReservationFilterOptions>(
    ['reservations-filter-options'],
    reservationsApi.getFilterOptions
  )

  const columns = useMemo(
    () =>
      getColumns({
        readers: filterOptions?.readers,
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
      description="Organizadas, claras y accesibles."
      pathname={pathname}
    />
  )
}
