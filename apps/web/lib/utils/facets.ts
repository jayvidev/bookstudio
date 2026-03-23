/**
 * Calculate facets for nested object properties.
 * Instead of counting rows, it sums numeric property values.
 */
export function calculateObjectPropertyFacets<T = any>(
  data: T[],
  accessor: string | ((row: T) => any),
  propertyKeys?: string[]
): Map<string, number> {
  const facets = new Map<string, number>()

  data.forEach((row) => {
    const columnValue =
      typeof accessor === 'function' ? accessor(row) : getNestedValue(row, accessor)

    if (columnValue && typeof columnValue === 'object') {
      const keys = propertyKeys || Object.keys(columnValue)

      keys.forEach((key) => {
        const value = columnValue[key]
        if (typeof value === 'number') {
          const currentCount = facets.get(key) || 0
          facets.set(key, currentCount + value)
        }
      })
    }
  })

  return facets
}

/** Helper: get nested value from object using dot notation */
function getNestedValue(obj: any, path: string): any {
  return path.split('.').reduce((current, key) => {
    return current && current[key] !== undefined ? current[key] : undefined
  }, obj)
}

/** Factory: creates a facet calculator for objects with numeric properties */
export function createObjectSumFacetCalculator<T = any>(
  accessor: string | ((row: T) => any),
  propertyKeys?: string[]
) {
  return (data: T[]): Map<string, number> => {
    return calculateObjectPropertyFacets(data, accessor, propertyKeys)
  }
}
