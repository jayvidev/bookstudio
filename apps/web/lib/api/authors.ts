import {
  type AuthorFilterOptions,
  authorFilterOptionsSchema,
} from '@/lib/schemas/author/author.filter.options.schema'
import { type AuthorList, authorListSchema } from '@/lib/schemas/author/author.list.schema'

import { apiClient } from './client'

export const authorsApi = {
  async getAll(): Promise<AuthorList[]> {
    const data = await apiClient.get('/authors')
    return authorListSchema.array().parse(data)
  },

  async getFilterOptions(): Promise<AuthorFilterOptions> {
    const data = await apiClient.get('/authors/filter-options')
    return authorFilterOptionsSchema.parse(data)
  },
}
