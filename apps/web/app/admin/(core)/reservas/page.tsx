import type { Metadata } from 'next'

import { ReservationsPage } from '@admin/pages/reservations'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/reservas'
const page = pageMap[PATHNAME]

const title = page.title
const resource = page.resource!

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <ReservationsPage title={title} pathname={PATHNAME} resource={resource} />
}
