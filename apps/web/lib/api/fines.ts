import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'
import {
  type FineFilterOptions,
  fineFilterOptionsSchema,
} from '@/lib/schemas/fine/fine.filter.options.schema'
import { type FineList, fineListSchema } from '@/lib/schemas/fine/fine.list.schema'

import { apiClient } from './client'

export const finesApi = {
  async getAll(): Promise<FineList[]> {
    const data = await apiClient.get('/fines?size=100')
    return pageResponseSchema(fineListSchema).parse(data).content
  },

  async getFilterOptions(): Promise<FineFilterOptions> {
    const data = await apiClient.get('/fines/filter-options')
    return fineFilterOptionsSchema.parse(data)
  },
}
