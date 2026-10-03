import { type CategoryList, categoryListSchema } from '@/lib/schemas/category/category.list.schema'
import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'

import { apiClient } from './client'

export const categoriesApi = {
  async getAll(): Promise<CategoryList[]> {
    const data = await apiClient.get('/categories?size=100')
    return pageResponseSchema(categoryListSchema).parse(data).content
  },
}
