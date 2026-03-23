import { Activity, CircleMinus, type LucideIcon } from 'lucide-react'

export const statusBadges = {
  ACTIVO: {
    label: 'Activo',
    icon: Activity,
    variant: 'success',
  },
  INACTIVO: {
    label: 'Inactivo',
    icon: CircleMinus,
    variant: 'muted',
  },
} as const

export type Status = keyof typeof statusBadges

export type StatusBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}
