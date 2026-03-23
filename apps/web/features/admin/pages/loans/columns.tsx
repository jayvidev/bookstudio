'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { BookCopy, BookOpenText, Calendar, Handshake } from 'lucide-react'

import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip'
import type { LoanFilterOptionsParams } from '@/lib/schemas/loan/loan.filter.options.schema'
import type { LoanList } from '@/lib/schemas/loan/loan.list.schema'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'
import { createObjectSumFacetCalculator } from '@/lib/utils/facets'

import { statusBadges } from './badges'
import { statusOptions } from './filter-options'

export const getColumns = (options?: LoanFilterOptionsParams): ColumnDef<LoanList>[] => {
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
      header: withMetaLabelHeader<LoanList>(),
      cell: ({ getValue }) => {
        const code = getValue<string>()
        return (
          <Badge variant="outline" className="font-mono">
            <Handshake className="mr-1" />
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
      header: withMetaLabelHeader<LoanList>(),
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
      header: withMetaLabelHeader<LoanList>(),
      cell: ({ row }) => {
        return row.original.reader.fullName
      },
      meta: withMetaLabelFilter<LoanList>({
        columnId: 'reader',
        options: readersOptions,
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      accessorKey: 'loanDate',
      header: withMetaLabelHeader<LoanList>(),
      meta: {
        dateRangeFilter: true,
        headerClass: 'text-center',
        cellClass: 'text-center',
      },
      cell: ({ getValue }) => {
        const loanDate = new Date(getValue<string>())
        const formatted = new Intl.DateTimeFormat('es-ES', {
          day: '2-digit',
          month: 'short',
          year: 'numeric',
        })
          .format(loanDate)
          .replace('.', '')

        return (
          <Badge variant="outline">
            <Calendar className="mr-1" />
            {formatted}
          </Badge>
        )
      },
      filterFn: (row, id, value) => {
        const rowDate = new Date(row.getValue(id))
        const [startDate, endDate] = value
        return rowDate >= startDate && rowDate <= endDate
      },
    },
    {
      id: 'items',
      accessorFn: (row) => String(row.itemCount),
      header: withMetaLabelHeader<LoanList>(),
      cell: ({ getValue }) => {
        const value = getValue<number>()
        return (
          <Badge variant="outline">
            <BookCopy className="mr-1" />
            {value}
          </Badge>
        )
      },
      meta: {
        headerClass: 'text-center',
        cellClass: 'text-center',
      },
    },
    {
      accessorKey: 'statusCounts',
      header: withMetaLabelHeader<LoanList>(),
      cell: ({ getValue }) => {
        const statusCounts = getValue<object>()

        return (
          <TooltipProvider>
            <div className="flex gap-1 justify-center">
              {Object.entries(statusCounts)
                .filter(([, count]) => count > 0)
                .map(([key, count]) => {
                  const status = statusBadges[key as keyof typeof statusBadges]
                  if (!status) return null

                  const Icon = status.icon
                  return (
                    <Tooltip key={key}>
                      <TooltipTrigger asChild>
                        <Badge variant={status.variant}>
                          <Icon className="mr-1" />
                          {count}
                        </Badge>
                      </TooltipTrigger>
                      <TooltipContent side="top">{status.label}</TooltipContent>
                    </Tooltip>
                  )
                })}
            </div>
          </TooltipProvider>
        )
      },
      enableSorting: false,
      meta: {
        headerClass: 'text-center',
        cellClass: 'text-center',
        ...withMetaLabelFilter<LoanList>({
          columnId: 'statusCounts',
          options: statusOptions,
        }),
        customFacetCalculator: createObjectSumFacetCalculator('statusCounts', [
          'borrowed',
          'returned',
          'overdue',
          'lost',
          'canceled',
        ]),
      },
      filterFn: (row, id, value) => {
        const statusCounts = row.getValue<typeof row.original.statusCounts>(id)
        return (value as (keyof typeof statusCounts)[]).some((status) => statusCounts?.[status] > 0)
      },
    },
    {
      id: 'actions',
      cell: () => <DataTableRowActions />,
    },
  ]
}
