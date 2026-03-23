import { z } from 'zod'

import { ReservationStatus } from './reservation.enums'

export const reservationListSchema = z.object({
  id: z.number(),
  code: z.string(),
  reader: z.object({
    id: z.number(),
    code: z.string(),
    fullName: z.string(),
  }),
  copy: z.object({
    code: z.string(),
  }),
  reservationDate: z.coerce.date(),
  status: ReservationStatus,
})

export type ReservationList = z.infer<typeof reservationListSchema>
