import type { Metadata } from 'next'

import { LoginPage } from '@auth/pages/login'

import { pageMap } from '@/config/page-map'

const page = pageMap['/iniciar-sesion']

export const metadata: Metadata = {
  title: page.title,
}

export default function Page() {
  return <LoginPage />
}
