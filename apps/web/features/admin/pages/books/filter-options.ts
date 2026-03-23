import { BookCheck, BookX, CircleCheck, XCircle } from 'lucide-react'

import { statusBadges } from '@admin/components/badges'

import { FilterOption } from '@/lib/types'

export const loanOptions: FilterOption[] = [
  {
    label: 'Sí',
    value: 'loaned',
    icon: BookCheck,
  },
  {
    label: 'No',
    value: 'notLoaned',
    icon: BookX,
  },
]

export const availabilityOptions: FilterOption[] = [
  {
    label: 'Sí',
    value: 'available',
    icon: CircleCheck,
  },
  {
    label: 'No',
    value: 'notAvailable',
    icon: XCircle,
  },
]

export const statusOptions: FilterOption[] = Object.entries(statusBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)
