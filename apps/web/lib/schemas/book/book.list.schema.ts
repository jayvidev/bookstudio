import { z } from 'zod'

import { Status } from '@/lib/schemas/common/api-enums'

export const bookListSchema = z.object({
  id: z.number(),
  isbn: z.string(),
  coverUrl: z.string().nullable(),
  title: z.string(),
  category: z.object({
    id: z.number(),
    name: z.string(),
  }),
  publisher: z.object({
    id: z.number(),
    name: z.string(),
  }),
  language: z.object({
    id: z.number(),
    code: z.string(),
    name: z.string(),
  }),
  copies: z.object({
    loaned: z.number(),
    available: z.number(),
  }),
  status: Status,
})

export type BookList = z.infer<typeof bookListSchema>
