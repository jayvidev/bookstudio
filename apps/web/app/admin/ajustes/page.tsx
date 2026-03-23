import type { Metadata } from 'next'

import { SettingsProfilePage } from '@admin/pages/settings/pages/profile'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/ajustes'
const page = pageMap[PATHNAME]

const title = page.title

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <SettingsProfilePage />
}
