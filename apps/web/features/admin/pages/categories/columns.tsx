'use client'

import type { ColumnDef } from '@tanstack/react-table'

import { statusBadges } from '@admin/components/badges'
import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import { CategoryList } from '@/lib/schemas/category/category.list.schema'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

import { levelBadges } from './badges'
import { levelOptions, statusOptions } from './filter-options'

export const columns: ColumnDef<CategoryList>[] = [
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
    header: withMetaLabelHeader<CategoryList>(),
    meta: {
      searchable: true,
    },
  },
  {
    accessorKey: 'level',
    header: withMetaLabelHeader<CategoryList>(),
    enableSorting: false,
    meta: withMetaLabelFilter<CategoryList>({
      columnId: 'level',
      options: levelOptions,
    }),
    cell: ({ row }) => {
      const meta = levelBadges[row.original.level]

      if (!meta) return null
      const Icon = meta.icon

      return (
        <Badge variant={meta.variant}>
          <Icon className="mr-1" />
          {meta.label}
        </Badge>
      )
    },
    filterFn: (row, id, value) => {
      return value.includes(row.getValue(id))
    },
  },
  {
    accessorKey: 'description',
    header: withMetaLabelHeader<CategoryList>(),
    cell: ({ getValue }) => {
      return <span className="truncate block max-w-[34rem] text-sm">{getValue<string>()}</span>
    },
    enableSorting: false,
  },
  {
    accessorKey: 'status',
    header: withMetaLabelHeader<CategoryList>(),
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
      ...withMetaLabelFilter<CategoryList>({
        columnId: 'status',
        options: statusOptions,
      }),
    },
    filterFn: (row, id, value) => {
      return value.includes(row.getValue(id))
    },
  },
  {
    id: 'actions',
    cell: () => <DataTableRowActions />,
  },
]
