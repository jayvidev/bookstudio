import type { ColumnDef } from '@tanstack/react-table'

type FilterOption = { label: string; value: string }

export type MetaFilter = {
  columnId: string
  options: FilterOption[]
  title: string
}

export function withMetaLabelFilter<TData>({
  columnId,
  options,
}: {
  columnId: (keyof TData & string) | string
  options: FilterOption[]
}): ColumnDef<TData>['meta'] {
  return {
    filter: {
      columnId,
      options,
      title: '',
    } as MetaFilter,
  }
}
