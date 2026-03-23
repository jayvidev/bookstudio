'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { BookText, Calendar, Handshake, History, OctagonAlert } from 'lucide-react'

import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import type { FineFilterOptionsParams } from '@/lib/schemas/fine/fine.filter.options.schema'
import type { FineList } from '@/lib/schemas/fine/fine.list.schema'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

import { statusBadges } from './badges'
import { statusOptions } from './filter-options'

export const getColumns = (options?: FineFilterOptionsParams): ColumnDef<FineList>[] => {
  const { loans: loansOptions = [], copies: copiesOptions = [] } = options ?? {}

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
      header: withMetaLabelHeader<FineList>(),
      cell: ({ getValue }) => {
        const code = getValue<string>()
        return (
          <Badge variant="outline" className="font-mono">
            <OctagonAlert className="mr-1" />
            {code}
          </Badge>
        )
      },
      meta: {
        searchable: true,
      },
    },
    {
      id: 'loanCode',
      accessorFn: (row) => String(row.loan.id),
      header: withMetaLabelHeader<FineList>(),
      cell: ({ row }) => {
        const code = row.original.loan.code
        return (
          <Badge variant="outline" className="font-mono">
            <Handshake className="mr-1" />
            {code}
          </Badge>
        )
      },
      meta: withMetaLabelFilter<FineList>({
        columnId: 'loanCode',
        options: loansOptions,
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      id: 'copyCode',
      accessorFn: (row) => String(row.copy.id),
      header: withMetaLabelHeader<FineList>(),
      cell: ({ row }) => {
        const code = row.original.copy.code
        return (
          <Badge variant="outline" className="font-mono">
            <BookText className="mr-1" />
            {code}
          </Badge>
        )
      },
      meta: withMetaLabelFilter<FineList>({
        columnId: 'copyCode',
        options: copiesOptions,
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      accessorKey: 'amount',
      header: withMetaLabelHeader<FineList>(),
      cell: ({ getValue }) => {
        const amount = getValue<number>()
        const formatted = `S/. ${amount.toFixed(2)}`

        return <Badge variant="outline">{formatted}</Badge>
      },
      meta: {
        headerClass: 'text-center',
        cellClass: 'text-center',
      },
    },
    {
      accessorKey: 'daysLate',
      header: withMetaLabelHeader<FineList>(),
      cell: ({ getValue }) => {
        const code = getValue<string>()
        return (
          <Badge variant="outline">
            <History className="mr-1" />
            {code}
          </Badge>
        )
      },
      meta: {
        headerClass: 'text-center',
        cellClass: 'text-center',
      },
    },
    {
      accessorKey: 'issuedAt',
      header: withMetaLabelHeader<FineList>(),
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
      header: withMetaLabelHeader<FineList>(),
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
        ...withMetaLabelFilter<FineList>({
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
