import { z } from 'zod'

import { FineStatus } from './fine.enums'

export const fineListSchema = z.object({
  id: z.number(),
  code: z.string(),
  loan: z.object({
    id: z.number(),
    code: z.string(),
  }),
  copy: z.object({
    id: z.number(),
    code: z.string(),
  }),
  amount: z.number(),
  daysLate: z.number().int(),
  issuedAt: z.coerce.date(),
  status: FineStatus,
})

export type FineList = z.infer<typeof fineListSchema>
