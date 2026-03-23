import type { Metadata } from 'next'

import { MaintenanceErrorPage } from '@error/pages/maintenance'

import { pageMap } from '@/config/page-map'

const page = pageMap['/error/503']

export const metadata: Metadata = {
  title: page.title,
}

export default function Page() {
  return <MaintenanceErrorPage />
}
