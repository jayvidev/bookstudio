import { z } from 'zod'

export const locationListSchema = z.object({
  id: z.number(),
  name: z.string(),
  description: z.string().nullable(),
  shelfCount: z.number(),
  bookCount: z.number(),
  copyCount: z.number(),
})

export type LocationList = z.infer<typeof locationListSchema>
