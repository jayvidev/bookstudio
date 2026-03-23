import type { Metadata } from 'next'

import { SettingsDisplayPage } from '@admin/pages/settings/pages/display'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/ajustes/visualizacion'
const page = pageMap[PATHNAME]

const title = page.title

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <SettingsDisplayPage />
}
