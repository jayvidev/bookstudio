import { z } from 'zod'

import { WorkerStatus } from './worker.enums'

export const workerListSchema = z.object({
  id: z.number(),
  profilePhotoUrl: z.string().nullable(),
  username: z.string(),
  email: z.email(),
  fullName: z.string(),
  role: z.object({
    id: z.number(),
    name: z.string(),
  }),
  status: WorkerStatus,
})

export type WorkerList = z.infer<typeof workerListSchema>
