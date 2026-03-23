import { ErrorLayout } from '@error/layout'

export function GeneralErrorPage() {
  return (
    <ErrorLayout
      status={500}
      title="¡Ups! Algo salió mal :')"
      message={
        <>
          Lamentamos el inconveniente. <br />
          Intenta nuevamente más tarde.
        </>
      }
    />
  )
}
