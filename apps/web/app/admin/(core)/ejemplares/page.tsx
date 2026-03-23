import type { Metadata } from 'next'

import { CopiesPage } from '@admin/pages/copies'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/ejemplares'
const page = pageMap[PATHNAME]

const title = page.title
const resource = page.resource!

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <CopiesPage title={title} pathname={PATHNAME} resource={resource} />
}
