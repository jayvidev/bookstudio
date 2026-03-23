'use client'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useListQuery } from '@/hooks/use-list-query'
import { locationsApi } from '@/lib/api/locations'
import type { LocationList } from '@/lib/schemas/location/location.list.schema'

import { columns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function LocationsPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<LocationList[]>(pathname, [resource], locationsApi.getAll)

  if (error) {
    console.error(`Failed to fetch ${resource}:`, error)
  }

  return (
    <TableListLayout
      columns={columns}
      data={data}
      resource={resource}
      title={title}
      description="Gestiona los espacios de tu biblioteca."
      pathname={pathname}
    />
  )
}
