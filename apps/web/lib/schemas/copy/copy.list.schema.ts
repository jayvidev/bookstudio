import { z } from 'zod'

import { CopyCondition, CopyStatus } from './copy.enums'

export const copyListSchema = z.object({
  id: z.number(),
  code: z.string(),
  book: z.object({
    id: z.number(),
    coverUrl: z.string().nullable(),
    title: z.string(),
  }),
  shelf: z.object({
    code: z.string(),
    floor: z.string(),
  }),
  location: z.object({
    name: z.string(),
  }),
  status: CopyStatus,
  condition: CopyCondition,
})

export type CopyList = z.infer<typeof copyListSchema>
