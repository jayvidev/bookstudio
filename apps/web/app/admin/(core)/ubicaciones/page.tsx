import type { Metadata } from 'next'

import { LocationsPage } from '@admin/pages/locations'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/ubicaciones'
const page = pageMap[PATHNAME]

const title = page.title
const resource = page.resource!

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <LocationsPage title={title} pathname={PATHNAME} resource={resource} />
}
