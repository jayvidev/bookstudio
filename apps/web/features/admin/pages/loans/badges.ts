import { AlertTriangle, Ban, Clock, Handshake, type LucideIcon, Undo2 } from 'lucide-react'

export const statusBadges = {
  borrowed: {
    label: 'Prestado',
    icon: Handshake,
    variant: 'info',
  },
  returned: {
    label: 'Devuelto',
    icon: Undo2,
    variant: 'success',
  },
  overdue: {
    label: 'Retrasado',
    icon: Clock,
    variant: 'warning',
  },
  lost: {
    label: 'Extraviado',
    icon: AlertTriangle,
    variant: 'danger',
  },
  canceled: {
    label: 'Cancelado',
    icon: Ban,
    variant: 'muted',
  },
} as const

export type Status = keyof typeof statusBadges
export type StatusBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}
