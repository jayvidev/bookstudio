import { z } from 'zod'

import { PaymentMethod } from './payment.enums'

export const paymentListSchema = z.object({
  id: z.number(),
  code: z.string(),
  fineCount: z.number(),
  reader: z.object({
    id: z.number(),
    code: z.string(),
    fullName: z.string(),
  }),
  amount: z.number(),
  paymentDate: z.coerce.date(),
  method: PaymentMethod,
})

export type PaymentList = z.infer<typeof paymentListSchema>
