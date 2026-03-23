import type { Metadata } from 'next'

import { GeneralErrorPage } from '@error/pages/general'

import { pageMap } from '@/config/page-map'

const page = pageMap['/error/500']

export const metadata: Metadata = {
  title: page.title,
}

export default function Page() {
  return <GeneralErrorPage />
}
