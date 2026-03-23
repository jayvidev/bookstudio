import { z } from 'zod'

export const ReaderType = z.enum(['ESTUDIANTE', 'DOCENTE', 'ADMINISTRATIVO', 'EXTERNO'])
export type ReaderType = z.infer<typeof ReaderType>

export const ReaderStatus = z.enum(['ACTIVO', 'SUSPENDIDO', 'BLOQUEADO', 'ELIMINADO'])
export type ReaderStatus = z.infer<typeof ReaderStatus>
