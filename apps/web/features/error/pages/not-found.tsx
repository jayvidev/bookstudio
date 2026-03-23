import { ErrorLayout } from '@error/layout'

export function NotFoundErrorPage() {
  return (
    <ErrorLayout
      status={404}
      title="¡Ups! Página no encontrada"
      message={
        <>
          Parece que la página que buscas <br />
          no existe o ha sido eliminada.
        </>
      }
    />
  )
}
