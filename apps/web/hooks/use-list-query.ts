import { useQuery } from '@tanstack/react-query'

import { staleTimes } from '@admin/config/stale-times'

export function useListQuery<T>(route: string, queryKey: string[], queryFn: () => Promise<T>) {
  return useQuery({
    queryKey,
    queryFn,
    staleTime: staleTimes[route] ?? 60_000,
    refetchOnWindowFocus: true,
  })
}
