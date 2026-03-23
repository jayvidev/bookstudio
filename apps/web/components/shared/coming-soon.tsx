import { Rocket } from 'lucide-react'

export function ComingSoon() {
  return (
    <div className="flex h-full w-full items-center justify-center px-4">
      <div className="flex flex-col items-center justify-center gap-2 text-center">
        <Rocket size={72} />
        <h1 className="text-4xl font-bold leading-tight">¡Próximamente!</h1>
        <p className="text-muted-foreground">
          Esta página aún no ha sido creada. <br />
          ¡Estate atento a las novedades!
        </p>
      </div>
    </div>
  )
}
