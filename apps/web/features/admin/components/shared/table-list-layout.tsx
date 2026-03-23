'use client'

import { ColumnDef } from '@tanstack/react-table'
import { CirclePlus, FileSpreadsheet, FileX } from 'lucide-react'

import { DataTable } from '@admin/components/data-table/data-table'
import { DataTableSkeleton } from '@admin/components/data-table/data-table-skeleton'
import { Breadcrumbs } from '@admin/components/shared/breadcrumbs'
import { sidebarMap } from '@admin/components/sidebar/sidebar-map'

import { Button } from '@/components/ui/button'
import { Spinner } from '@/components/ui/spinner'

interface TableListLayoutProps<TData, TValue> {
  columns: ColumnDef<TData, TValue>[]
  data?: TData[]
  resource: string
  title: string
  description: string
  pathname: string
  filterCount?: number
  dateRangeCount?: number
}

function getFilterCount<TData, TValue>(
  columns: ColumnDef<TData, TValue>[],
  filterCount: number | undefined
): number {
  if (filterCount !== undefined) return filterCount
  return columns.filter((col) => {
    const meta = col.meta as Record<string, unknown> | undefined
    return meta?.filter && !meta?.dateRangeFilter
  }).length
}

function getDateRangeCount<TData, TValue>(
  columns: ColumnDef<TData, TValue>[],
  dateRangeCount: number | undefined
): number {
  if (dateRangeCount !== undefined) return dateRangeCount
  return columns.filter((col) => {
    const meta = col.meta as Record<string, unknown> | undefined
    return meta?.dateRangeFilter
  }).length
}

export function TableListLayout<TData, TValue>({
  columns,
  data,
  resource,
  title,
  description,
  pathname,
  filterCount,
  dateRangeCount,
}: TableListLayoutProps<TData, TValue>) {
  const computedFilterCount = getFilterCount(columns, filterCount)
  const computedDateRangeCount = getDateRangeCount(columns, dateRangeCount)
  const isLoading = data === undefined

  const sidebarMeta = sidebarMap[pathname as keyof typeof sidebarMap]
  const Icon = sidebarMeta?.icon

  return (
    <>
      <Breadcrumbs pathname={pathname} />
      <div className="space-y-4">
        <div className="mb-2 flex flex-wrap items-center justify-between space-y-2 gap-x-4">
          <div>
            <h1 className="text-2xl font-bold flex items-center gap-2">
              {Icon && <Icon strokeWidth={2.5} />}
              {title}
            </h1>
            <p className="text-muted-foreground">{description}</p>
          </div>
          <div className={`flex gap-2 ${isLoading ? 'cursor-wait' : ''}`}>
            <Button variant="outline" disabled={isLoading}>
              {isLoading ? <Spinner /> : <FileX className="text-green-500 dark:text-green-400" />}
              Excel
            </Button>
            <Button variant="outline" disabled={isLoading}>
              {isLoading ? <Spinner /> : <FileSpreadsheet className="text-destructive" />}
              PDF
            </Button>
            <Button disabled={isLoading}>
              {isLoading ? <Spinner /> : <CirclePlus />}
              Agregar
            </Button>
          </div>
        </div>
        {isLoading ? (
          <DataTableSkeleton
            columnCount={columns.length}
            filterCount={computedFilterCount}
            dateRangeCount={computedDateRangeCount}
          />
        ) : (
          <DataTable columns={columns} data={data ?? []} resource={resource} />
        )}
      </div>
    </>
  )
}
