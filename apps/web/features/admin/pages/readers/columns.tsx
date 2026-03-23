'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { BookOpenText, Mail, Phone } from 'lucide-react'

import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import { ReaderList } from '@/lib/schemas/reader/reader.list.schema'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

import { statusBadges, typeBadges } from './badges'
import { statusOptions, typeOptions } from './filter-options'

export const columns: ColumnDef<ReaderList>[] = [
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
    accessorKey: 'code',
    header: withMetaLabelHeader<ReaderList>(),
    cell: ({ getValue }) => {
      const code = getValue<string>()
      return (
        <Badge variant="outline" className="font-mono">
          <BookOpenText className="mr-1" />
          {code}
        </Badge>
      )
    },
  },
  {
    accessorKey: 'fullName',
    header: withMetaLabelHeader<ReaderList>(),
    meta: {
      searchable: true,
    },
  },
  {
    accessorKey: 'phone',
    header: withMetaLabelHeader<ReaderList>(),
    cell: ({ getValue }) => {
      const phone = getValue<string>()
      const formattedPhone = phone ? `+51${phone}` : undefined

      return (
        <Badge variant="outline">
          <Phone className="mr-1" />
          {phone ? (
            <a href={`tel:${formattedPhone}`} className="hover:underline">
              {phone}
            </a>
          ) : (
            '—'
          )}
        </Badge>
      )
    },
    enableSorting: false,
  },
  {
    accessorKey: 'email',
    header: withMetaLabelHeader<ReaderList>(),
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
    accessorKey: 'type',
    header: withMetaLabelHeader<ReaderList>(),
    cell: ({ row }) => {
      const meta = typeBadges[row.original.type]

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
      ...withMetaLabelFilter<ReaderList>({
        columnId: 'type',
        options: typeOptions,
      }),
    },
    filterFn: (row, id, value) => {
      return value.includes(String(row.getValue(id)))
    },
  },
  {
    accessorKey: 'status',
    header: withMetaLabelHeader<ReaderList>(),
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
      ...withMetaLabelFilter<ReaderList>({
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
