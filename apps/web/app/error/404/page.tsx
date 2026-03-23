import type { Metadata } from 'next'

import { NotFoundErrorPage } from '@error/pages/not-found'

import { pageMap } from '@/config/page-map'

const page = pageMap['/error/404']

export const metadata: Metadata = {
  title: page.title,
}

export default function Page() {
  return <NotFoundErrorPage />
}
