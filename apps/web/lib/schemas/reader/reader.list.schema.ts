import { z } from 'zod'

import { ReaderStatus, ReaderType } from './reader.enums'

export const readerListSchema = z.object({
  id: z.number(),
  code: z.string(),
  fullName: z.string(),
  phone: z.string(),
  email: z.email(),
  type: ReaderType,
  status: ReaderStatus,
})

export type ReaderList = z.infer<typeof readerListSchema>
