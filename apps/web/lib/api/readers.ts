import { type ReaderList, readerListSchema } from '@/lib/schemas/reader/reader.list.schema'

import { apiClient } from './client'

export const readersApi = {
  async getAll(): Promise<ReaderList[]> {
    const data = await apiClient.get('/readers')
    return readerListSchema.array().parse(data)
  },
}
