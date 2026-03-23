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
    const data = await apiClient.get('/publishers')
    return publisherListSchema.array().parse(data)
  },

  async getFilterOptions(): Promise<PublisherFilterOptions> {
    const data = await apiClient.get('/publishers/filter-options')
    return publisherFilterOptionsSchema.parse(data)
  },
}
