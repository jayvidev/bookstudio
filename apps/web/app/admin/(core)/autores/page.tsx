import type { Metadata } from 'next'

import { AuthorsPage } from '@admin/pages/authors'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/autores'
const page = pageMap[PATHNAME]

const title = page.title
const resource = page.resource!

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <AuthorsPage title={title} pathname={PATHNAME} resource={resource} />
}
