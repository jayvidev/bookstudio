'use client'

import { TableListLayout } from '@admin/components/shared/table-list-layout'

import { useListQuery } from '@/hooks/use-list-query'
import { categoriesApi } from '@/lib/api/categories'
import type { CategoryList } from '@/lib/schemas/category/category.list.schema'

import { columns } from './columns'

interface Props {
  title: string
  pathname: string
  resource: string
}

export function CategoriesPage({ title, pathname, resource }: Props) {
  const { data, error } = useListQuery<CategoryList[]>(pathname, [resource], categoriesApi.getAll)

  if (error) {
    console.error(`Failed to fetch ${resource}:`, error)
  }

  return (
    <TableListLayout
      columns={columns}
      data={data}
      resource={resource}
      title={title}
      description="Organiza tus libros por temas fácilmente."
      pathname={pathname}
    />
  )
}
