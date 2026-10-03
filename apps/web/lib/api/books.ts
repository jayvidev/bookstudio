import {
  type BookFilterOptions,
  bookFilterOptionsSchema,
} from '@/lib/schemas/book/book.filter.options.schema'
import { type BookList, bookListSchema } from '@/lib/schemas/book/book.list.schema'
import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'

import { apiClient } from './client'

export const booksApi = {
  async getAll(): Promise<BookList[]> {
    const data = await apiClient.get('/books?size=100')
    return pageResponseSchema(bookListSchema).parse(data).content
  },

  async getFilterOptions(): Promise<BookFilterOptions> {
    const data = await apiClient.get('/books/filter-options')
    return bookFilterOptionsSchema.parse(data)
  },
}
