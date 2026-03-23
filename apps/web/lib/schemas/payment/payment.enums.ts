import { z } from 'zod'

export const PaymentMethod = z.enum(['EFECTIVO', 'TARJETA', 'TRANSFERENCIA', 'CHEQUE', 'OTROS'])
export type PaymentMethod = z.infer<typeof PaymentMethod>
