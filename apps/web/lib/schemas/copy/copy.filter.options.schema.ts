import { z } from 'zod'

import { optionsArraySchema } from '@/lib/schemas/common/option.schema'

export const copiesFilterOptionsSchema = z.object({
  books: optionsArraySchema,
})

export type CopiesFilterOptions = z.infer<typeof copiesFilterOptionsSchema>

export type CopiesFilterOptionsParams = Partial<CopiesFilterOptions>
