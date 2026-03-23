'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { BookOpenText, BookText, Calendar, Calendar1 } from 'lucide-react'

import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import type { ReservationFilterOptionsParams } from '@/lib/schemas/reservation/reservation.filter.options.schema'
import type { ReservationList } from '@/lib/schemas/reservation/reservation.list.schema'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

import { statusBadges } from './badges'
import { statusOptions } from './filter-options'

export const getColumns = (
  options?: ReservationFilterOptionsParams
): ColumnDef<ReservationList>[] => {
  const { readers: readersOptions = [] } = options ?? {}

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
      accessorKey: 'code',
      header: withMetaLabelHeader<ReservationList>(),
      cell: ({ getValue }) => {
        const code = getValue<string>()
        return (
          <Badge variant="outline" className="font-mono">
            <Calendar1 className="mr-1" />
            {code}
          </Badge>
        )
      },
      meta: {
        searchable: true,
      },
    },
    {
      id: 'readerCode',
      accessorFn: (row) => String(row.reader.code),
      header: withMetaLabelHeader<ReservationList>(),
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
      id: 'reader',
      accessorFn: (row) => String(row.reader.id),
      header: withMetaLabelHeader<ReservationList>(),
      cell: ({ row }) => {
        return row.original.reader.fullName
      },
      meta: withMetaLabelFilter<ReservationList>({
        columnId: 'reader',
        options: readersOptions,
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      id: 'copyCode',
      accessorFn: (row) => String(row.copy.code),
      header: withMetaLabelHeader<ReservationList>(),
      cell: ({ getValue }) => {
        const code = getValue<string>()
        return (
          <Badge variant="outline" className="font-mono">
            <BookText className="mr-1" />
            {code}
          </Badge>
        )
      },
    },
    {
      accessorKey: 'reservationDate',
      header: withMetaLabelHeader<ReservationList>(),
      cell: ({ getValue }) => {
        const date = new Date(getValue<string>())
        const formatted = new Intl.DateTimeFormat('es-ES', {
          day: '2-digit',
          month: 'short',
          year: 'numeric',
        })
          .format(date)
          .replace('.', '')

        return (
          <Badge variant="outline">
            <Calendar className="mr-1" />
            {formatted}
          </Badge>
        )
      },
      meta: {
        dateRangeFilter: true,
        headerClass: 'text-center',
        cellClass: 'text-center',
      },
      filterFn: (row, id, value) => {
        const rowDate = new Date(row.getValue(id))
        const [startDate, endDate] = value
        return rowDate >= startDate && rowDate <= endDate
      },
    },
    {
      accessorKey: 'status',
      header: withMetaLabelHeader<ReservationList>(),
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
        ...withMetaLabelFilter<ReservationList>({
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
}
