import type { Metadata } from 'next'

import { ResetPasswordPage } from '@auth/pages/reset-password'

import { pageMap } from '@/config/page-map'

const page = pageMap['/restablecer-contrasena']

export const metadata: Metadata = {
  title: page.title,
}

export default function Page() {
  return <ResetPasswordPage />
}
