import type { Metadata } from 'next'

import { CategoriesPage } from '@admin/pages/categories'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/categorias'
const page = pageMap[PATHNAME]

const title = page.title
const resource = page.resource!

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <CategoriesPage title={title} pathname={PATHNAME} resource={resource} />
}
