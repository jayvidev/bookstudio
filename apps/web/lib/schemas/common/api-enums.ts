import { z } from 'zod'

export const Status = z.enum(['ACTIVO', 'INACTIVO'])
export type Status = z.infer<typeof Status>
