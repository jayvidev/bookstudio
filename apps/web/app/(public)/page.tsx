import type { Metadata } from 'next'

import { HomePage } from '@public/pages/home'

export const metadata: Metadata = {
  title: 'Inicio',
}

export default function Page() {
  return <HomePage />
}
