import { CheckCircle, CircleSlash, Clock, type LucideIcon } from 'lucide-react'

export const statusBadges = {
  PENDIENTE: {
    label: 'Pendiente',
    icon: Clock,
    variant: 'warning',
  },
  PAGADO: {
    label: 'Pagado',
    icon: CheckCircle,
    variant: 'success',
  },
  CONDONADO: {
    label: 'Condonado',
    icon: CircleSlash,
    variant: 'muted',
  },
} as const

export type Status = keyof typeof statusBadges

export type StatusBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}
