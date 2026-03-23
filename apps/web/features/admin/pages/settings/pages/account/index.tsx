import { ContentSection } from '@admin/pages/settings/components/content-section'

import { AccountForm } from './form'

export function SettingsAccountPage() {
  return (
    <ContentSection
      title="Cuenta"
      desc="Actualiza la contraseña de tu cuenta. Configura tu idioma preferido."
    >
      <AccountForm />
    </ContentSection>
  )
}
