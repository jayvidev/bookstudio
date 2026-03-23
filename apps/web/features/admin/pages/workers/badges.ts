import { Activity, AlertTriangle, type LucideIcon, Trash } from 'lucide-react'

export const statusBadges = {
  ACTIVO: {
    label: 'Activo',
    icon: Activity,
    variant: 'success',
  },
  SUSPENDIDO: {
    label: 'Suspendido',
    icon: AlertTriangle,
    variant: 'warning',
  },
  ELIMINADO: {
    label: 'Eliminado',
    icon: Trash,
    variant: 'danger',
  },
} as const

export type Status = keyof typeof statusBadges

export type StatusBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}
