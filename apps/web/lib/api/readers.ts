import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'
import { type ReaderList, readerListSchema } from '@/lib/schemas/reader/reader.list.schema'

import { apiClient } from './client'

export const readersApi = {
  async getAll(): Promise<ReaderList[]> {
    const data = await apiClient.get('/readers?size=100')
    return pageResponseSchema(readerListSchema).parse(data).content
  },
}
