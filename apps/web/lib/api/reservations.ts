import { pageResponseSchema } from '@/lib/schemas/common/page-response.schema'
import {
  type ReservationFilterOptions,
  reservationFilterOptionsSchema,
} from '@/lib/schemas/reservation/reservation.filter.options.schema'
import {
  type ReservationList,
  reservationListSchema,
} from '@/lib/schemas/reservation/reservation.list.schema'

import { apiClient } from './client'

export const reservationsApi = {
  async getAll(): Promise<ReservationList[]> {
    const data = await apiClient.get('/reservations?size=100')
    return pageResponseSchema(reservationListSchema).parse(data).content
  },

  async getFilterOptions(): Promise<ReservationFilterOptions> {
    const data = await apiClient.get('/reservations/filter-options')
    return reservationFilterOptionsSchema.parse(data)
  },
}
