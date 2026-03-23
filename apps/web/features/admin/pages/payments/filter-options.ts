import { FilterOption } from '@/lib/types'

import { methodBadges } from './badges'

export const methodsOptions: FilterOption[] = Object.entries(methodBadges).map(
  ([key, { label, icon }]) => ({
    value: key,
    label,
    icon,
  })
)
