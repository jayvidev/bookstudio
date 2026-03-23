'use client'

import { useMemo } from 'react'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useFilterOptions } from '@/hooks/use-filter-options'
import { useListQuery } from '@/hooks/use-list-query'
import { loansApi } from '@/lib/api/loans'
import type { LoanFilterOptions } from '@/lib/schemas/loan/loan.filter.options.schema'
import type { LoanList } from '@/lib/schemas/loan/loan.list.schema'

import { getColumns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function LoansPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<LoanList[]>(pathname, [resource], loansApi.getAll)
  const { data: filterOptions } = useFilterOptions<LoanFilterOptions>(
    ['loans-filter-options'],
    loansApi.getFilterOptions
  )

  const columns = useMemo(
    () =>
      getColumns({
        readers: filterOptions?.readers,
      }),
    [filterOptions]
  )

  if (error) {
    console.error(`Failed to fetch ${resource}:`, error)
  }

  return (
    <TableListLayout
      columns={columns}
      data={data}
      resource={resource}
      title={title}
      description="Todo lo que necesitas para gestionarlos."
      pathname={pathname}
    />
  )
}
