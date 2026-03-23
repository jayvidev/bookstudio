import { useQuery, UseQueryResult } from '@tanstack/react-query'

const FILTER_STALE_TIME = 1000 * 60 * 5

export function useFilterOptions<T>(
  queryKey: string[],
  queryFn: () => Promise<T>
): UseQueryResult<T, Error> {
  return useQuery<T, Error>({
    queryKey,
    queryFn,
    staleTime: FILTER_STALE_TIME,
  })
}
