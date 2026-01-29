import { z } from 'zod'

import { optionsArraySchema } from '@/lib/schemas/common/option.schema'

export const loanFilterOptionsSchema = z.object({
  readers: optionsArraySchema,
})

export type LoanFilterOptions = z.infer<typeof loanFilterOptionsSchema>

export type LoanFilterOptionsParams = Partial<LoanFilterOptions>
