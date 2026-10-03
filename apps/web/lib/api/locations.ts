import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'
import { type LocationList, locationListSchema } from '@/lib/schemas/location/location.list.schema'

import { apiClient } from './client'

export const locationsApi = {
  async getAll(): Promise<LocationList[]> {
    const data = await apiClient.get('/locations?size=100')
    return pageResponseSchema(locationListSchema).parse(data).content
  },
}
