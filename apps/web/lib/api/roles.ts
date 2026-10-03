import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'
import { type RoleList, roleListSchema } from '@/lib/schemas/role/role.list.schema'

import { apiClient } from './client'

export const rolesApi = {
  async getAll(): Promise<RoleList[]> {
    const data = await apiClient.get('/roles?size=100')
    return pageResponseSchema(roleListSchema).parse(data).content
  },
}
