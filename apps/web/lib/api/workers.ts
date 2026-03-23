import {
  type WorkerFilterOptions,
  workerFilterOptionsSchema,
} from '@/lib/schemas/worker/worker.filter.options.schema'
import { type WorkerList, workerListSchema } from '@/lib/schemas/worker/worker.list.schema'

import { apiClient } from './client'

export const workersApi = {
  async getAll(): Promise<WorkerList[]> {
    const data = await apiClient.get('/workers')
    return workerListSchema.array().parse(data)
  },

  async getFilterOptions(): Promise<WorkerFilterOptions> {
    const data = await apiClient.get('/workers/filter-options')
    return workerFilterOptionsSchema.parse(data)
  },
}
