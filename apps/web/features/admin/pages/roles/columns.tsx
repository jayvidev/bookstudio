'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { KeyRound } from 'lucide-react'

import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import { RoleList } from '@/lib/schemas/role/role.list.schema'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

export const columns: ColumnDef<RoleList>[] = [
  {
    id: 'select',
    header: ({ table }) => (
      <Checkbox
        checked={
          table.getIsAllPageRowsSelected() || (table.getIsSomePageRowsSelected() && 'indeterminate')
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
    accessorKey: 'name',
    header: withMetaLabelHeader<RoleList>(),
    meta: {
      searchable: true,
    },
  },
  {
    accessorKey: 'description',
    header: withMetaLabelHeader<RoleList>(),
    cell: ({ getValue }) => {
      return <span className="truncate block max-w-[60rem] text-sm">{getValue<string>()}</span>
    },
    enableSorting: false,
  },
  {
    id: 'permissions',
    accessorFn: (row) => row.permissionCount,
    header: withMetaLabelHeader<RoleList>(),
    cell: ({ getValue }) => {
      const value = getValue<number>()
      const displayValue = value === 0 ? '-' : value
      const variant = value === 0 ? 'muted' : 'warning'

      return (
        <Badge variant={variant}>
          <KeyRound className="mr-1" />
          {displayValue}
        </Badge>
      )
    },
    meta: {
      headerClass: 'text-center',
      cellClass: 'text-center',
    },
  },
  {
    id: 'actions',
    cell: () => <DataTableRowActions />,
  },
]
