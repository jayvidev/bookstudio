import type { Metadata } from 'next'

import { BooksPage } from '@admin/pages/books'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/libros'
const page = pageMap[PATHNAME]

const title = page.title
const resource = page.resource!

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <BooksPage title={title} pathname={PATHNAME} resource={resource} />
}
