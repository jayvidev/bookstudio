import { z } from 'zod'

export const ReservationStatus = z.enum(['PENDIENTE', 'CANCELADA', 'ATENDIDA', 'EXPIRADA'])
export type ReservationStatus = z.infer<typeof ReservationStatus>
