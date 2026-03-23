import type { Metadata } from 'next'

import { ComingSoon } from '@/components/shared/coming-soon'
import { pageMap } from '@/config/page-map'

const PATHNAME = '/admin/centro-de-ayuda'
const page = pageMap[PATHNAME]

const title = page.title

export const metadata: Metadata = {
  title,
}

export default function Page() {
  return <ComingSoon />
}
