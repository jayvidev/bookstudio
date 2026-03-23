import {
  type PaymentFilterOptions,
  paymentFilterOptionsSchema,
} from '@/lib/schemas/payment/payment.filter.options.schema'
import { type PaymentList, paymentListSchema } from '@/lib/schemas/payment/payment.list.schema'

import { apiClient } from './client'

export const paymentsApi = {
  async getAll(): Promise<PaymentList[]> {
    const data = await apiClient.get('/payments')
    return paymentListSchema.array().parse(data)
  },

  async getFilterOptions(): Promise<PaymentFilterOptions> {
    const data = await apiClient.get('/payments/filter-options')
    return paymentFilterOptionsSchema.parse(data)
  },
}
