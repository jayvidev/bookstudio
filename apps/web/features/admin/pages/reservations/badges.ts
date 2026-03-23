import { Ban, CheckCircle, Clock, type LucideIcon, TimerOff } from 'lucide-react'

export const statusBadges = {
  PENDIENTE: {
    label: 'Pendiente',
    icon: Clock,
    variant: 'warning',
  },
  CANCELADA: {
    label: 'Cancelada',
    icon: Ban,
    variant: 'danger',
  },
  ATENDIDA: {
    label: 'Atendida',
    icon: CheckCircle,
    variant: 'success',
  },
  EXPIRADA: {
    label: 'Expirada',
    icon: TimerOff,
    variant: 'muted',
  },
} as const

export type Status = keyof typeof statusBadges

export type StatusBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}
