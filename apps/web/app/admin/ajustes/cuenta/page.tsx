import type { Metadata } from 'next'

import { SettingsAccountPage } from '@admin/pages/settings/pages/account'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/ajustes/cuenta'
const page = pageMap[PATHNAME]

const title = page.title

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <SettingsAccountPage />
}
