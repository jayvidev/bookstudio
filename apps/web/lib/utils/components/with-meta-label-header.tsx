'use client'

import type { HeaderContext } from '@tanstack/react-table'

import { DataTableColumnHeader } from '@admin/components/data-table/data-table-column-header'
import { getColumnLabel } from '@admin/config/column-labels'

export function withMetaLabelHeader<TData, TValue = unknown>() {
  function MetaLabelHeader({ column }: HeaderContext<TData, TValue>) {
    return (
      <DataTableColumnHeader
        column={column}
        title={getColumnLabel(column.columnDef.meta?.resource, column.id)}
      />
    )
  }
  MetaLabelHeader.displayName = 'MetaLabelHeader'
  return MetaLabelHeader
}
