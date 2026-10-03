import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'
import {
  type LoanFilterOptions,
  loanFilterOptionsSchema,
} from '@/lib/schemas/loan/loan.filter.options.schema'
import { type LoanList, loanListSchema } from '@/lib/schemas/loan/loan.list.schema'

import { apiClient } from './client'

export const loansApi = {
  async getAll(): Promise<LoanList[]> {
    const data = await apiClient.get('/loans?size=100')
    return pageResponseSchema(loanListSchema).parse(data).content
  },

  async getFilterOptions(): Promise<LoanFilterOptions> {
    const data = await apiClient.get('/loans/filter-options')
    return loanFilterOptionsSchema.parse(data)
  },
}
