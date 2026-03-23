import { Backpack, GraduationCap, Layers, type LucideIcon, NotebookText } from 'lucide-react'

export const levelBadges = {
  PRIMARIA: {
    label: 'Primaria',
    icon: Backpack,
    variant: 'warning',
  },
  SECUNDARIA: {
    label: 'Secundaria',
    icon: NotebookText,
    variant: 'info',
  },
  SUPERIOR: {
    label: 'Superior',
    icon: GraduationCap,
    variant: 'brand',
  },
  GENERAL: {
    label: 'General',
    icon: Layers,
    variant: 'muted',
  },
} as const

export type Level = keyof typeof levelBadges

export type LevelBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}
