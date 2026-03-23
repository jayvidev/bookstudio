import {
  Activity,
  AlertTriangle,
  CircleMinus,
  CircleQuestionMark,
  GraduationCap,
  type LucideIcon,
  PencilRuler,
  Trash,
  UserCog,
} from 'lucide-react'

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
  BLOQUEADO: {
    label: 'Bloqueado',
    icon: CircleMinus,
    variant: 'muted',
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

export const typeBadges = {
  ESTUDIANTE: {
    label: 'Estudiante',
    icon: GraduationCap,
    variant: 'bright',
  },
  DOCENTE: {
    label: 'Docente',
    icon: PencilRuler,
    variant: 'brand',
  },
  ADMINISTRATIVO: {
    label: 'Administrativo',
    icon: UserCog,
    variant: 'info',
  },
  EXTERNO: {
    label: 'Externo',
    icon: CircleQuestionMark,
    variant: 'muted',
  },
} as const

export type Type = keyof typeof typeBadges

export type TypeBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}
