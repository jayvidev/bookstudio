import { z } from 'zod'

export const FineStatus = z.enum(['PENDIENTE', 'PAGADO', 'CONDONADO'])
export type FineStatus = z.infer<typeof FineStatus>
