import { statusBadges } from '@admin/components/badges'

import { FilterOption } from '@/lib/types'

export const statusOptions: FilterOption[] = Object.entries(statusBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)
