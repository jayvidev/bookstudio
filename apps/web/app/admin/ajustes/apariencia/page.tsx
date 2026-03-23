import type { Metadata } from 'next'

import { SettingsAppearancePage } from '@admin/pages/settings/pages/appearance'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/ajustes/apariencia'
const page = pageMap[PATHNAME]

const title = page.title

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <SettingsAppearancePage />
}
