import { z } from 'zod'

import { optionsArraySchema } from '@/lib/schemas/common/option.schema'

export const fineFilterOptionsSchema = z.object({
  loans: optionsArraySchema,
  copies: optionsArraySchema,
})

export type FineFilterOptions = z.infer<typeof fineFilterOptionsSchema>

export type FineFilterOptionsParams = Partial<FineFilterOptions>
