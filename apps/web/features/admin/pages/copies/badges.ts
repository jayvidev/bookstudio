import {
  AlertTriangle,
  BadgeCheck,
  Ban,
  CircleCheckBig,
  Clock,
  Handshake,
  type LucideIcon,
  MinusCircle,
  ThumbsUp,
  Wrench,
} from 'lucide-react'

export const conditionBadges = {
  NUEVO: {
    label: 'Nuevo',
    icon: BadgeCheck,
    variant: 'brand',
  },
  BUENO: {
    label: 'Bueno',
    icon: ThumbsUp,
    variant: 'bright',
  },
  REGULAR: {
    label: 'Regular',
    icon: MinusCircle,
    variant: 'muted',
  },
  MALO: {
    label: 'Malo',
    icon: AlertTriangle,
    variant: 'warning',
  },
  DETERIORADO: {
    label: 'Deteriorado',
    icon: Ban,
    variant: 'danger',
  },
} as const

export type Condition = keyof typeof conditionBadges

export type ConditionBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}

export const statusBadges = {
  DISPONIBLE: {
    label: 'Disponible',
    icon: CircleCheckBig,
    variant: 'success',
  },
  PRESTADO: {
    label: 'Prestado',
    icon: Handshake,
    variant: 'info',
  },
  RESERVADO: {
    label: 'Reservado',
    icon: Clock,
    variant: 'warning',
  },
  EXTRAVIADO: {
    label: 'Extraviado',
    icon: AlertTriangle,
    variant: 'danger',
  },
  MANTENIMIENTO: {
    label: 'Mantenimiento',
    icon: Wrench,
    variant: 'muted',
  },
} as const

export type Status = keyof typeof statusBadges

export type StatusBadgeMeta = {
  label: string
  icon: LucideIcon
  variant: string
}
