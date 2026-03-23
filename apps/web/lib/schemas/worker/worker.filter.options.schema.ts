import { z } from 'zod'

import { optionsArraySchema } from '@/lib/schemas/common/option.schema'

export const workerFilterOptionsSchema = z.object({
  roles: optionsArraySchema,
})

export type WorkerFilterOptions = z.infer<typeof workerFilterOptionsSchema>

export type WorkerFilterOptionsParams = Partial<WorkerFilterOptions>
