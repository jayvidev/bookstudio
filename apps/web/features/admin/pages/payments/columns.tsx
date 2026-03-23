'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { BookOpenText, Calendar, DollarSign, ListChecks } from 'lucide-react'

import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import type { PaymentFilterOptionsParams } from '@/lib/schemas/payment/payment.filter.options.schema'
import type { PaymentList } from '@/lib/schemas/payment/payment.list.schema'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

import { methodBadges } from './badges'
import { methodsOptions } from './filter-options'

export const getColumns = (options?: PaymentFilterOptionsParams): ColumnDef<PaymentList>[] => {
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
      header: withMetaLabelHeader<PaymentList>(),
      cell: ({ getValue }) => {
        const code = getValue<string>()
        return (
          <Badge variant="outline" className="font-mono">
            <DollarSign className="mr-1" />
            {code}
          </Badge>
        )
      },
      meta: {
        searchable: true,
      },
    },
    {
      accessorKey: 'fineCount',
      header: withMetaLabelHeader<PaymentList>(),
      cell: ({ getValue }) => {
        const code = getValue<string>()
        return (
          <Badge variant="outline">
            <ListChecks className="mr-1" />
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
      id: 'readerCode',
      accessorFn: (row) => String(row.reader.code),
      header: withMetaLabelHeader<PaymentList>(),
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
      header: withMetaLabelHeader<PaymentList>(),
      cell: ({ row }) => {
        return row.original.reader.fullName
      },
      meta: withMetaLabelFilter<PaymentList>({
        columnId: 'reader',
        options: readersOptions,
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      accessorKey: 'amount',
      header: withMetaLabelHeader<PaymentList>(),
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
      accessorKey: 'paymentDate',
      header: withMetaLabelHeader<PaymentList>(),
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
      accessorKey: 'method',
      header: withMetaLabelHeader<PaymentList>(),
      cell: ({ row }) => {
        const meta = methodBadges[row.original.method]

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
        ...withMetaLabelFilter<PaymentList>({
          columnId: 'method',
          options: methodsOptions,
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
