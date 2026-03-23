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
    const data = await apiClient.get('/reservations')
    return reservationListSchema.array().parse(data)
  },

  async getFilterOptions(): Promise<ReservationFilterOptions> {
    const data = await apiClient.get('/reservations/filter-options')
    return reservationFilterOptionsSchema.parse(data)
  },
}
