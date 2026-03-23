import { type CategoryList, categoryListSchema } from '@/lib/schemas/category/category.list.schema'

import { apiClient } from './client'

export const categoriesApi = {
  async getAll(): Promise<CategoryList[]> {
    const data = await apiClient.get('/categories')
    return categoryListSchema.array().parse(data)
  },
}
