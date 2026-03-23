import { Skeleton } from '@/components/ui/skeleton'
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'
import { cn } from '@/lib/utils'

export interface DataTableSkeletonProps extends React.ComponentProps<'div'> {
  columnCount: number
  rowCount?: number
  filterCount?: number
  dateRangeCount?: number
  cellWidths?: string[]
  withViewOptions?: boolean
  withPagination?: boolean
  withSearch?: boolean
  shrinkZero?: boolean
}

export function DataTableSkeleton({
  columnCount,
  rowCount = 10,
  filterCount = 0,
  dateRangeCount = 0,
  cellWidths = ['auto'],
  withViewOptions = true,
  withPagination = true,
  withSearch = true,
  shrinkZero = false,
  className,
  ...props
}: DataTableSkeletonProps) {
  const cozyCellWidths = Array.from(
    { length: columnCount },
    (_, index) => cellWidths[index % cellWidths.length] ?? 'auto'
  )

  return (
    <div className={cn('flex w-full flex-col gap-4 overflow-auto', className)} {...props}>
      <div className="flex flex-wrap items-center justify-between space-x-2 gap-2">
        <div className="flex flex-wrap items-center gap-2 cursor-wait">
          {withSearch ? <Skeleton className="h-8 w-[190px] lg:w-[270px]" /> : null}
          {filterCount > 0
            ? Array.from({ length: filterCount }).map((_, i) => (
                <Skeleton key={i} className="h-8 w-25 border-dashed" />
              ))
            : null}
          {dateRangeCount > 0
            ? Array.from({ length: dateRangeCount }).map((_, i) => (
                <Skeleton key={`date-${i}`} className="h-8 w-[240px]" />
              ))
            : null}
        </div>

        <div className="flex items-center gap-2 cursor-wait">
          {withViewOptions ? <Skeleton className="hidden h-8 w-22 lg:block" /> : null}
        </div>
      </div>
      <div className="rounded-md border">
        <Table className="cursor-wait">
          <TableHeader>
            {Array.from({ length: 1 }).map((_, i) => (
              <TableRow key={i} className="hover:bg-transparent">
                {Array.from({ length: columnCount }).map((_, j) => (
                  <TableHead
                    key={j}
                    style={{
                      width: cozyCellWidths[j],
                      minWidth: shrinkZero ? cozyCellWidths[j] : '9.5rem',
                    }}
                  >
                    <Skeleton className="h-6 w-full" />
                  </TableHead>
                ))}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {Array.from({ length: rowCount }).map((_, i) => (
              <TableRow key={i} className="hover:bg-transparent">
                {Array.from({ length: columnCount }).map((_, j) => (
                  <TableCell
                    key={j}
                    style={{
                      width: cozyCellWidths[j],
                      minWidth: shrinkZero ? cozyCellWidths[j] : '9.5rem',
                    }}
                  >
                    <Skeleton className="h-6 my-1 w-full" />
                  </TableCell>
                ))}
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
      {withPagination ? (
        <div className="flex flex-col items-center justify-between space-y-4 px-2 lg:flex-row lg:space-y-0">
          <div className="flex flex-col items-center space-y-2 sm:flex-row sm:space-x-6 sm:space-y-0 cursor-wait">
            <Skeleton className="h-8 w-58 shrink-0" />
            <div className="flex items-center gap-2">
              <Skeleton className="h-8 w-17" />
              <Skeleton className="h-8 w-26" />
            </div>
          </div>
          <div className="flex items-center space-x-4 cursor-wait">
            <div className="flex items-center justify-center font-medium text-sm">
              <Skeleton className="h-8 w-22" />
            </div>
            <div className="flex items-center space-x-2">
              <Skeleton className="h-8 w-8" />
              <Skeleton className="h-8 w-8" />
              <Skeleton className="h-8 w-8" />
              <Skeleton className="h-8 w-8" />
            </div>
          </div>
        </div>
      ) : null}
    </div>
  )
}
