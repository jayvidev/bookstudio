import { statusBadges } from '@admin/components/badges'

import { FilterOption } from '@/lib/types'

import { levelBadges } from './badges'

export const levelOptions: FilterOption[] = Object.entries(levelBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)

export const statusOptions: FilterOption[] = Object.entries(statusBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)
