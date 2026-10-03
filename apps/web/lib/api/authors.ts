import {
  type AuthorFilterOptions,
  authorFilterOptionsSchema,
} from '@/lib/schemas/author/author.filter.options.schema'
import { type AuthorList, authorListSchema } from '@/lib/schemas/author/author.list.schema'
import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'

import { apiClient } from './client'

export const authorsApi = {
  async getAll(): Promise<AuthorList[]> {
    const data = await apiClient.get('/authors?size=100')
    return pageResponseSchema(authorListSchema).parse(data).content
  },

  async getFilterOptions(): Promise<AuthorFilterOptions> {
    const data = await apiClient.get('/authors/filter-options')
    return authorFilterOptionsSchema.parse(data)
  },
}
