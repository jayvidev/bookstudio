import type { Metadata } from 'next'

import { UnauthorisedErrorPage } from '@error/pages/unauthorized'

import { pageMap } from '@/config/page-map'

const page = pageMap['/error/401']

export const metadata: Metadata = {
  title: page.title,
}

export default function Page() {
  return <UnauthorisedErrorPage />
}
