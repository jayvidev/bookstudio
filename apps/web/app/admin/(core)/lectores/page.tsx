import type { Metadata } from 'next'

import { ReadersPage } from '@admin/pages/readers'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/lectores'
const page = pageMap[PATHNAME]

const title = page.title
const resource = page.resource!

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <ReadersPage title={title} pathname={PATHNAME} resource={resource} />
}
