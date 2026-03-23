import { ErrorLayout } from '@error/layout'

export function ForbiddenErrorPage() {
  return (
    <ErrorLayout
      status={403}
      title="Acceso denegado"
      message={
        <>
          No tienes los permisos necesarios <br />
          para ver esta página.
        </>
      }
    />
  )
}
