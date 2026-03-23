import { FilterOption } from '@/lib/types'

import { conditionBadges, statusBadges } from './badges'

export const statusOptions: FilterOption[] = Object.entries(statusBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)

export const conditionsOptions: FilterOption[] = Object.entries(conditionBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)
