import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'
import {
  type PublisherFilterOptions,
  publisherFilterOptionsSchema,
} from '@/lib/schemas/publisher/publisher.filter.options.schema'
import {
  type PublisherList,
  publisherListSchema,
} from '@/lib/schemas/publisher/publisher.list.schema'

import { apiClient } from './client'

export const publishersApi = {
  async getAll(): Promise<PublisherList[]> {
    const data = await apiClient.get('/publishers?size=100')
    return pageResponseSchema(publisherListSchema).parse(data).content
  },

  async getFilterOptions(): Promise<PublisherFilterOptions> {
    const data = await apiClient.get('/publishers/filter-options')
    return publisherFilterOptionsSchema.parse(data)
  },
}
