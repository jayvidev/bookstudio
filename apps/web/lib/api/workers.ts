import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'
import {
  type WorkerFilterOptions,
  workerFilterOptionsSchema,
} from '@/lib/schemas/worker/worker.filter.options.schema'
import { type WorkerList, workerListSchema } from '@/lib/schemas/worker/worker.list.schema'

import { apiClient } from './client'

export const workersApi = {
  async getAll(): Promise<WorkerList[]> {
    const data = await apiClient.get('/workers?size=100')
    return pageResponseSchema(workerListSchema).parse(data).content
  },

  async getFilterOptions(): Promise<WorkerFilterOptions> {
    const data = await apiClient.get('/workers/filter-options')
    return workerFilterOptionsSchema.parse(data)
  },
}
