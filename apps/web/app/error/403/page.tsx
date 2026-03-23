import type { Metadata } from 'next'

import { ForbiddenErrorPage } from '@error/pages/forbidden'

import { pageMap } from '@/config/page-map'

const page = pageMap['/error/403']

export const metadata: Metadata = {
  title: page.title,
}

export default function Page() {
  return <ForbiddenErrorPage />
}
