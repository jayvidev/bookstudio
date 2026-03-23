import { FilterOption } from '@/lib/types'

import { statusBadges, typeBadges } from './badges'

export const statusOptions: FilterOption[] = Object.entries(statusBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)

export const typeOptions: FilterOption[] = Object.entries(typeBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)
