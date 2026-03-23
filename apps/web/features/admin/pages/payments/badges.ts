import { Banknote, CreditCard, DollarSign, FileText, type LucideIcon, Receipt } from 'lucide-react'

export const methodBadges = {
  EFECTIVO: {
    label: 'Efectivo',
    icon: DollarSign,
    variant: 'success',
  },
  TARJETA: {
    label: 'Tarjeta',
    icon: CreditCard,
    variant: 'brand',
  },
  TRANSFERENCIA: {
    label: 'Transferencia',
    icon: Banknote,
    variant: 'info',
  },
  CHEQUE: {
    label: 'Cheque',
    icon: Receipt,
    variant: 'warning',
  },
  OTROS: {
    label: 'Otros',
    icon: FileText,
    variant: 'muted',
  },
} as const

export type Method = keyof typeof methodBadges

export type MethodBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}
