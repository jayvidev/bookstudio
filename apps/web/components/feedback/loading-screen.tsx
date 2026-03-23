import { Logo } from '@/components/shared/logo'

export default function LoadingScreen() {
  return (
    <div className="flex flex-col items-center justify-center h-screen bg-background">
      <Logo className="size-20" />
    </div>
  )
}
