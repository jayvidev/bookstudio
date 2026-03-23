import {
  type FineFilterOptions,
  fineFilterOptionsSchema,
} from '@/lib/schemas/fine/fine.filter.options.schema'
import { type FineList, fineListSchema } from '@/lib/schemas/fine/fine.list.schema'

import { apiClient } from './client'

export const finesApi = {
  async getAll(): Promise<FineList[]> {
    const data = await apiClient.get('/fines')
    return fineListSchema.array().parse(data)
  },

  async getFilterOptions(): Promise<FineFilterOptions> {
    const data = await apiClient.get('/fines/filter-options')
    return fineFilterOptionsSchema.parse(data)
  },
}
