import { z } from 'zod'

import { optionsArraySchema } from '@/lib/schemas/common/option.schema'

export const bookFilterOptionsSchema = z.object({
  categories: optionsArraySchema,
  publishers: optionsArraySchema,
  languages: optionsArraySchema,
})

export type BookFilterOptions = z.infer<typeof bookFilterOptionsSchema>

export type BookFilterOptionsParams = Partial<BookFilterOptions>
