import type { Metadata } from 'next'

import { ForgotPasswordPage } from '@auth/pages/forgot-password'

import { pageMap } from '@/config/page-map'

const page = pageMap['/recuperar-contrasena']

export const metadata: Metadata = {
  title: page.title,
}

export default function Page() {
  return <ForgotPasswordPage />
}
