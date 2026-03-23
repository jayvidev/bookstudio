import { type LocationList, locationListSchema } from '@/lib/schemas/location/location.list.schema'

import { apiClient } from './client'

export const locationsApi = {
  async getAll(): Promise<LocationList[]> {
    const data = await apiClient.get('/locations')
    return locationListSchema.array().parse(data)
  },
}
