import { z } from 'zod'

import { optionsArraySchema } from '@/lib/schemas/common/option.schema'

export const paymentFilterOptionsSchema = z.object({
  readers: optionsArraySchema,
})

export type PaymentFilterOptions = z.infer<typeof paymentFilterOptionsSchema>

export type PaymentFilterOptionsParams = Partial<PaymentFilterOptions>
