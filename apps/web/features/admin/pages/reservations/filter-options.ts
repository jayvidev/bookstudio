import { FilterOption } from '@/lib/types'

import { statusBadges } from './badges'

export const statusOptions: FilterOption[] = Object.entries(statusBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)
