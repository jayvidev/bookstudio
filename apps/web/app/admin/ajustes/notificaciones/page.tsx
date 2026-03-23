import type { Metadata } from 'next'

import { SettingsNotificationsPage } from '@admin/pages/settings/pages/notifications'

import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/ajustes/notificaciones'
const page = pageMap[PATHNAME]

const title = page.title

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <SettingsNotificationsPage />
}
