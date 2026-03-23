import '@tanstack/react-table'

export type ColumnFilterMeta = {
  columnId?: string
  title?: string
  options: { label: string; value: string }[]
}

declare module '@tanstack/react-table' {
  interface ColumnMeta {
    filter?: ColumnFilterMeta
    searchable?: boolean
    dateRangeFilter?: boolean
    cellClass?: string
    headerClass?: string
    customFacetCalculator?: (data: unknown[]) => Map<string, number>
    resource?: string
  }
}
