import { z } from 'zod'

import { optionsArraySchema } from '@/lib/schemas/common/option.schema'

export const authorFilterOptionsSchema = z.object({
  nationalities: optionsArraySchema,
})

export type AuthorFilterOptions = z.infer<typeof authorFilterOptionsSchema>

export type AuthorFilterOptionsParams = Partial<AuthorFilterOptions>
