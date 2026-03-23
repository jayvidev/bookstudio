import { ReactNode } from 'react'

import { SettingsLayout } from '@admin/pages/settings/layout'

interface Props {
  children: ReactNode
}

export default function Layout({ children }: Props) {
  return <SettingsLayout>{children}</SettingsLayout>
}
