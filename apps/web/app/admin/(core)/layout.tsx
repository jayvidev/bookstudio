import { ReactNode } from 'react'

interface Props {
  children: ReactNode
}

export default function Layout({ children }: Props) {
  return <div className="flex flex-1 p-5 gap-4 flex-col">{children}</div>
}
