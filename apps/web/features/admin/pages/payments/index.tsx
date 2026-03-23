'use client'

import { useMemo } from 'react'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useFilterOptions } from '@/hooks/use-filter-options'
import { useListQuery } from '@/hooks/use-list-query'
import { paymentsApi } from '@/lib/api/payments'
import type { PaymentFilterOptions } from '@/lib/schemas/payment/payment.filter.options.schema'
import type { PaymentList } from '@/lib/schemas/payment/payment.list.schema'

import { getColumns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function PaymentsPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<PaymentList[]>(pathname, [resource], paymentsApi.getAll)
  const { data: filterOptions } = useFilterOptions<PaymentFilterOptions>(
    ['payments-filter-options'],
    paymentsApi.getFilterOptions
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
      description="Historial de transacciones."
      pathname={pathname}
    />
  )
}
