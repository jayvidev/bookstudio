'use client'

import { useState } from 'react'

import { Table } from '@tanstack/react-table'
import { Search, Trash, X } from 'lucide-react'
import { DateRange } from 'react-day-picker'

import { getColumnLabel } from '@admin/config/column-labels'

import { Button } from '@/components/ui/button'
import { DateRangePicker } from '@/components/ui/date-range-picker'
import { Input } from '@/components/ui/input'
import { FilterOption } from '@/lib/types'

import { DataTableFacetedFilter } from './data-table-faceted-filter'
import { DataTableViewOptions } from './data-table-view-options'

interface DataTableToolbarProps<TData> {
  table: Table<TData>
  resource: string
}

export function DataTableToolbar<TData>({ table, resource }: DataTableToolbarProps<TData>) {
  const isFiltered = table.getState().columnFilters.length > 0

  const searchCol = table.getAllLeafColumns().find((col) => col.columnDef.meta?.searchable)

  const dateRangeCols = table
    .getAllLeafColumns()
    .filter((col) => col.columnDef.meta?.dateRangeFilter)

  const searchValue =
    (searchCol ? (table.getColumn(searchCol.id)?.getFilterValue() as string) : '') ?? ''

  const [dateRanges, setDateRanges] = useState<Record<string, DateRange | null>>({})

  const handleDateSelect = (columnId: string, range: DateRange | undefined) => {
    const updatedDateRanges = { ...dateRanges }

    if (!range || (!range.from && !range.to)) {
      updatedDateRanges[columnId] = null
      table.getColumn(columnId)?.setFilterValue(undefined)
      setDateRanges(updatedDateRanges)
      return
    }

    updatedDateRanges[columnId] = range
    setDateRanges(updatedDateRanges)

    if (range.from && range.to) {
      table.getColumn(columnId)?.setFilterValue([range.from, range.to])
    } else if (range.from) {
      table.getColumn(columnId)?.setFilterValue([range.from, range.from])
    }
  }

  return (
    <div className="flex flex-wrap items-center justify-between space-x-2 gap-2">
      <div className="flex flex-wrap items-center gap-2">
        {searchCol && (
          <div className="relative">
            <Input
              placeholder={`Buscar por ${getColumnLabel(resource, searchCol.id).toLowerCase()}…`}
              className="pl-9 pr-4 h-8 w-[190px] lg:w-[270px] text-sm"
              value={searchValue}
              onChange={(e) => table.getColumn(searchCol.id)?.setFilterValue(e.target.value)}
            />
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
          </div>
        )}

        {table
          .getAllLeafColumns()
          .filter((col) => col.columnDef.meta?.filter)
          .map((col) => {
            const { title, options } = col.columnDef.meta!.filter! as {
              title: string
              options: FilterOption[]
            }

            return (
              <DataTableFacetedFilter
                key={col.id}
                column={col}
                title={title}
                options={options}
                table={table}
              />
            )
          })}

        {dateRangeCols.map((col) => {
          return (
            <DateRangePicker
              key={col.id}
              value={dateRanges[col.id] || undefined}
              onDateSelect={(range) => handleDateSelect(col.id, range)}
              placeholder={`Rango de ${getColumnLabel(resource, col.id).toLowerCase()}`}
            />
          )
        })}

        {isFiltered && (
          <Button
            className="border-dashed"
            variant="outline"
            size="sm"
            onClick={() => {
              table.resetColumnFilters()
              setDateRanges({})
            }}
          >
            Resetear
            <X className="h-4 w-4" />
          </Button>
        )}
      </div>

      <div className="flex items-center gap-2">
        {table.getFilteredSelectedRowModel().rows.length > 0 ? (
          <Button
            variant="outline"
            size="sm"
            className="text-destructive hover:text-destructive data-[highlighted]:text-destructive"
          >
            <Trash aria-hidden="true" className="text-current" />
            Eliminar ({table.getFilteredSelectedRowModel().rows.length})
          </Button>
        ) : null}
        <DataTableViewOptions table={table} resource={resource} />
      </div>
    </div>
  )
}
