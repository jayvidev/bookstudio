'use client'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useListQuery } from '@/hooks/use-list-query'
import { rolesApi } from '@/lib/api/roles'
import type { RoleList } from '@/lib/schemas/role/role.list.schema'

import { columns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function RolesPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<RoleList[]>(pathname, [resource], rolesApi.getAll)

  if (error) {
    console.error(`Failed to fetch ${resource}:`, error)
  }

  return (
    <TableListLayout
      columns={columns}
      data={data}
      resource={resource}
      title={title}
      description="Define permisos fácilmente."
      pathname={pathname}
    />
  )
}
