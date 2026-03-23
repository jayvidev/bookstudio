import {
  type CopiesFilterOptions,
  copiesFilterOptionsSchema,
} from '@/lib/schemas/copy/copy.filter.options.schema'
import { type CopyList, copyListSchema } from '@/lib/schemas/copy/copy.list.schema'

import { apiClient } from './client'

export const copiesApi = {
  async getAll(): Promise<CopyList[]> {
    const data = await apiClient.get('/copies')
    return copyListSchema.array().parse(data)
  },

  async getFilterOptions(): Promise<CopiesFilterOptions> {
    const data = await apiClient.get('/copies/filter-options')
    return copiesFilterOptionsSchema.parse(data)
  },
}
