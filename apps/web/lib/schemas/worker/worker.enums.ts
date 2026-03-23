import { z } from 'zod'

export const WorkerStatus = z.enum(['ACTIVO', 'SUSPENDIDO', 'ELIMINADO'])
export type WorkerStatus = z.infer<typeof WorkerStatus>
