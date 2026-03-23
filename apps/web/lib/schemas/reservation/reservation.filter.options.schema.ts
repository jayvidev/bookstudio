import { z } from 'zod'

import { optionsArraySchema } from '@/lib/schemas/common/option.schema'

export const reservationFilterOptionsSchema = z.object({
  readers: optionsArraySchema,
})

export type ReservationFilterOptions = z.infer<typeof reservationFilterOptionsSchema>

export type ReservationFilterOptionsParams = Partial<ReservationFilterOptions>
