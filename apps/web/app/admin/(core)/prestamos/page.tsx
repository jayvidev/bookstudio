import type { Metadata } from 'next'

import { LoansPage } from '@admin/pages/loans'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/prestamos'
const page = pageMap[PATHNAME]

const title = page.title
const resource = page.resource!

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <LoansPage title={title} pathname={PATHNAME} resource={resource} />
}
