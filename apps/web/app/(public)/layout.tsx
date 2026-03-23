import { ReactNode } from 'react'

import { PublicLayout } from '@public/layout'

interface Props {
  children: ReactNode
}

export default function Layout({ children }: Props) {
  return <PublicLayout>{children}</PublicLayout>
}
