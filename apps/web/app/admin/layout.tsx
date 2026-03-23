import { ReactNode } from 'react'

import { AdminLayout } from '@admin/layout'

interface Props {
  children: ReactNode
}

export default function Layout({ children }: Props) {
  return <AdminLayout>{children}</AdminLayout>
}
