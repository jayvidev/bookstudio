import { z } from 'zod'

import { optionsArraySchema } from '@/lib/schemas/common/option.schema'

export const publisherFilterOptionsSchema = z.object({
  nationalities: optionsArraySchema,
})

export type PublisherFilterOptions = z.infer<typeof publisherFilterOptionsSchema>

export type PublisherFilterOptionsParams = Partial<PublisherFilterOptions>
