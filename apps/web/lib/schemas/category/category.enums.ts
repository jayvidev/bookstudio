import { z } from 'zod'

export const CategoryLevel = z.enum(['PRIMARIA', 'SECUNDARIA', 'SUPERIOR', 'GENERAL'])
export type CategoryLevel = z.infer<typeof CategoryLevel>
