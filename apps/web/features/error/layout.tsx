'use client'

import { ArrowLeft, Home } from 'lucide-react'
import { useRouter } from 'next/navigation'

import { Button } from '@/components/ui/button'

interface Props {
  status?: number
  title: string
  message: string | React.ReactNode
  showActions?: boolean
  actions?: React.ReactNode
}

export function ErrorLayout({ status, title, message, showActions = true, actions }: Props) {
  const router = useRouter()

  return (
    <div className="h-svh">
      <div className="m-auto flex h-full w-full flex-col items-center justify-center gap-2">
        {status && <h1 className="text-[7rem] leading-tight font-bold">{status}</h1>}
        <span className="font-medium text-2xl">{title}</span>
        <p className="text-muted-foreground text-center">{message}</p>
        {showActions && (
          <div className="mt-6 flex gap-4">
            {actions ?? (
              <>
                <Button variant="outline" onClick={() => router.back()}>
                  <ArrowLeft />
                  Volver
                </Button>
                <Button onClick={() => router.push('/')}>
                  <Home />
                  Ir al inicio
                </Button>
              </>
            )}
          </div>
        )}
      </div>
    </div>
  )
}
