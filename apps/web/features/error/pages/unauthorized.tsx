import { ErrorLayout } from '@error/layout'

export function UnauthorisedErrorPage() {
  return (
    <ErrorLayout
      status={401}
      title="Acceso no autorizado"
      message={
        <>
          Por favor, inicia sesión con las credenciales adecuadas <br />
          para acceder a este recurso.
        </>
      }
    />
  )
}
