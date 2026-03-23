'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { Mail, ShieldCheck } from 'lucide-react'

import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import type { WorkerFilterOptionsParams } from '@/lib/schemas/worker/worker.filter.options.schema'
import type { WorkerList } from '@/lib/schemas/worker/worker.list.schema'
import { getInitials } from '@/lib/utils'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

import { statusBadges } from './badges'
import { statusOptions } from './filter-options'

export const getColumns = (options?: WorkerFilterOptionsParams): ColumnDef<WorkerList>[] => {
  const { roles: rolesOptions = [] } = options ?? {}

  return [
    {
      id: 'select',
      header: ({ table }) => (
        <Checkbox
          checked={
            table.getIsAllPageRowsSelected() ||
            (table.getIsSomePageRowsSelected() && 'indeterminate')
          }
          onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
          aria-label="Seleccionar todo"
          className="translate-y-0.5"
        />
      ),
      cell: ({ row }) => (
        <Checkbox
          checked={row.getIsSelected()}
          onCheckedChange={(value) => row.toggleSelected(!!value)}
          aria-label="Seleccionar fila"
          className="translate-y-0.5"
        />
      ),
      enableSorting: false,
      enableHiding: false,
    },
    {
      accessorKey: 'username',
      header: withMetaLabelHeader<WorkerList>(),
      cell: ({ getValue, row }) => {
        const username = getValue<string>()
        const fullName = row.original.fullName
        const photoUrl = row.original.profilePhotoUrl

        return (
          <div className="flex items-center gap-2">
            <Avatar>
              {photoUrl ? (
                <AvatarImage src={photoUrl} alt={`Foto de ${fullName}`} className="object-cover" />
              ) : (
                <AvatarFallback className="text-xs">{getInitials(fullName)}</AvatarFallback>
              )}
            </Avatar>
            <span>{username}</span>
          </div>
        )
      },
      meta: {
        searchable: true,
      },
    },
    {
      accessorKey: 'email',
      header: withMetaLabelHeader<WorkerList>(),
      cell: ({ getValue }) => {
        const email = getValue<string>()
        return (
          <Badge variant="outline">
            <Mail className="mr-1" />
            <a href={`mailto:${email}`} className="hover:underline">
              {email}
            </a>
          </Badge>
        )
      },
      enableSorting: false,
    },
    {
      accessorKey: 'fullName',
      header: withMetaLabelHeader<WorkerList>(),
    },
    {
      id: 'role',
      accessorFn: (row) => String(row.role.id),
      header: withMetaLabelHeader<WorkerList>(),
      cell: ({ row }) => {
        const name = row.original.role.name
        return (
          <Badge variant="outline">
            <ShieldCheck className="mr-1" />
            {name}
          </Badge>
        )
      },
      enableSorting: false,
      meta: withMetaLabelFilter<WorkerList>({
        columnId: 'role',
        options: rolesOptions,
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      accessorKey: 'status',
      header: withMetaLabelHeader<WorkerList>(),
      cell: ({ row }) => {
        const meta = statusBadges[row.original.status]

        if (!meta) return null
        const Icon = meta.icon

        return (
          <Badge variant={meta.variant}>
            <Icon className="mr-1" />
            {meta.label}
          </Badge>
        )
      },
      enableSorting: false,
      meta: {
        headerClass: 'text-center',
        cellClass: 'text-center',
        ...withMetaLabelFilter<WorkerList>({
          columnId: 'status',
          options: statusOptions,
        }),
      },
      filterFn: (row, id, value) => {
        return value.includes(String(row.getValue(id)))
      },
    },
    {
      id: 'actions',
      cell: () => <DataTableRowActions />,
    },
  ]
}
