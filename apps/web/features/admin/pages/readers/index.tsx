'use client'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useListQuery } from '@/hooks/use-list-query'
import { readersApi } from '@/lib/api/readers'
import type { ReaderList } from '@/lib/schemas/reader/reader.list.schema'

import { columns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function ReadersPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<ReaderList[]>(pathname, [resource], readersApi.getAll)

  if (error) {
    console.error(`Failed to fetch ${resource}:`, error)
  }

  return (
    <TableListLayout
      columns={columns}
      data={data}
      resource={resource}
      title={title}
      description="Seguimiento de miembros."
      pathname={pathname}
    />
  )
}
