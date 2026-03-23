import { type RoleList, roleListSchema } from '@/lib/schemas/role/role.list.schema'

import { apiClient } from './client'

export const rolesApi = {
  async getAll(): Promise<RoleList[]> {
    const data = await apiClient.get('/roles')
    return roleListSchema.array().parse(data)
  },
}
