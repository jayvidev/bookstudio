import { z } from 'zod'

export const CopyStatus = z.enum([
  'DISPONIBLE',
  'PRESTADO',
  'RESERVADO',
  'EXTRAVIADO',
  'MANTENIMIENTO',
])
export type CopyStatus = z.infer<typeof CopyStatus>

export const CopyCondition = z.enum(['NUEVO', 'BUENO', 'REGULAR', 'MALO', 'DETERIORADO'])
export type CopyCondition = z.infer<typeof CopyCondition>
