'use client'

import * as React from 'react'

import type { Row } from '@tanstack/react-table'
import {
  ColumnDef,
  ColumnFiltersState,
  flexRender,
  getCoreRowModel,
  getFacetedRowModel,
  getFacetedUniqueValues,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  SortingState,
  useReactTable,
  VisibilityState,
} from '@tanstack/react-table'

import { getColumnLabel } from '@admin/config/column-labels'

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'
import { cn } from '@/lib/utils'

import { DataTablePagination } from './data-table-pagination'
import { DataTableToolbar } from './data-table-toolbar'

const normalizeText = (text: string): string => {
  return text
    .toLowerCase()
    .normalize('NFD')
    .replace(/[\u0300-\u036f]/g, '')
    .trim()
}

const createSmartFilter = <TData,>() => {
  return (row: Row<TData>, columnId: string, filterValue: string) => {
    if (!filterValue) return true

    const cellValue = row.getValue(columnId)
    if (cellValue === null) return false

    const normalizedCellValue = normalizeText(String(cellValue))
    const normalizedFilterValue = normalizeText(String(filterValue))

    return normalizedCellValue.includes(normalizedFilterValue)
  }
}

interface DataTableProps<TData, TValue> {
  columns: ColumnDef<TData, TValue>[]
  data: TData[]
  resource: string
}

export function DataTable<TData, TValue>({
  columns,
  data,
  resource,
}: DataTableProps<TData, TValue>) {
  const [rowSelection, setRowSelection] = React.useState({})
  const [columnVisibility, setColumnVisibility] = React.useState<VisibilityState>({})
  const [columnFilters, setColumnFilters] = React.useState<ColumnFiltersState>([])
  const [sorting, setSorting] = React.useState<SortingState>([])
  const [pagination, setPagination] = React.useState({
    pageIndex: 0,
    pageSize: 10,
  })

  const enhancedColumns = React.useMemo(() => {
    return columns.map((column): ColumnDef<TData, TValue> => {
      const meta = {
        ...column.meta,
        resource,
      }

      if (meta?.filter) {
        const filter = { ...meta.filter }
        const filterColumnId: string =
          filter.columnId ?? column.id ?? (column as { accessorKey?: string }).accessorKey ?? ''

        if (!filter.title) {
          filter.title = getColumnLabel(resource, filterColumnId)
        }

        filter.columnId = filterColumnId
        meta.filter = filter
      }

      const updatedColumn: ColumnDef<TData, TValue> = {
        ...column,
        meta,
      }

      if (column.meta?.searchable) {
        updatedColumn.filterFn = createSmartFilter<TData>()
      }

      return updatedColumn
    })
  }, [columns, resource])

  const table = useReactTable({
    data,
    columns: enhancedColumns,
    state: {
      sorting,
      columnVisibility,
      rowSelection,
      columnFilters,
      pagination,
    },
    onPaginationChange: setPagination,
    onRowSelectionChange: setRowSelection,
    onSortingChange: setSorting,
    onColumnFiltersChange: setColumnFilters,
    onColumnVisibilityChange: setColumnVisibility,
    getCoreRowModel: getCoreRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFacetedRowModel: getFacetedRowModel(),
    getFacetedUniqueValues: getFacetedUniqueValues(),
    enableRowSelection: true,
  })

  return (
    <div className="space-y-4">
      <DataTableToolbar table={table} resource={resource} />
      <div className="overflow-y-auto rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => (
                  <TableHead
                    key={header.id}
                    colSpan={header.colSpan}
                    className={cn('px-4', header.column.columnDef.meta?.headerClass)}
                  >
                    {header.isPlaceholder
                      ? null
                      : flexRender(header.column.columnDef.header, header.getContext())}
                  </TableHead>
                ))}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row, index) => (
                <TableRow
                  key={row.id}
                  data-state={row.getIsSelected() && 'selected'}
                  className="table-row-animate"
                  style={{
                    animationDelay: `${index * 0.05}s`,
                  }}
                >
                  {row.getVisibleCells().map((cell) => (
                    <TableCell
                      key={cell.id}
                      className={cn('px-4', cell.column.columnDef.meta?.cellClass)}
                    >
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={columns.length} className="h-14 text-center">
                  Sin resultados.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
      <DataTablePagination table={table} />
    </div>
  )
}
