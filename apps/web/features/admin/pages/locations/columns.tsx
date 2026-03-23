'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { Archive, BookCopy, Boxes } from 'lucide-react'

import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import type { LocationList } from '@/lib/schemas/location/location.list.schema'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

const getBadgeVariant = (value: number) => {
  if (value === 0) return 'muted'
  if (value <= 10) return 'warning'
  return 'brand'
}

export const columns: ColumnDef<LocationList>[] = [
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
    header: withMetaLabelHeader<LocationList>(),
    meta: {
      searchable: true,
    },
  },
  {
    accessorKey: 'description',
    header: withMetaLabelHeader<LocationList>(),
    cell: ({ getValue }) => {
      return <span className="truncate block max-w-[30rem] text-sm">{getValue<string>()}</span>
    },
    enableSorting: false,
  },
  {
    id: 'shelves',
    accessorFn: (row) => row.shelfCount,
    header: withMetaLabelHeader<LocationList>(),
    cell: ({ getValue }) => {
      const value = getValue<number>()
      const displayValue = value === 0 ? '-' : value
      return (
        <Badge variant={getBadgeVariant(value)}>
          <Archive className="mr-1" />
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
    id: 'books',
    accessorFn: (row) => row.bookCount,
    header: withMetaLabelHeader<LocationList>(),
    cell: ({ getValue }) => {
      const value = getValue<number>()
      const displayValue = value === 0 ? '-' : value
      return (
        <Badge variant={getBadgeVariant(value)}>
          <BookCopy className="mr-1" />
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
    id: 'copies',
    accessorFn: (row) => row.copyCount,
    header: withMetaLabelHeader<LocationList>(),
    cell: ({ getValue }) => {
      const value = getValue<number>()
      const displayValue = value === 0 ? '-' : value
      return (
        <Badge variant={getBadgeVariant(value)}>
          <Boxes className="mr-1" />
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
