import { z } from 'zod'

import { Status } from '@/lib/schemas/common/api-enums'

export const publisherListSchema = z.object({
  id: z.number(),
  photoUrl: z.string().nullable(),
  name: z.string(),
  nationality: z.object({
    id: z.number(),
    code: z.string(),
    name: z.string(),
  }),
  website: z.string().nullable(),
  address: z.string().nullable(),
  status: Status,
})

export type PublisherList = z.infer<typeof publisherListSchema>
