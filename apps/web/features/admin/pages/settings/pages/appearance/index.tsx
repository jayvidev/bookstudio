import { ContentSection } from '@admin/pages/settings/components/content-section'

import { AppearanceForm } from './form'

export function SettingsAppearancePage() {
  return (
    <ContentSection
      title="Apariencia"
      desc="Personaliza la apariencia de la app. Cambia automáticamente entre los temas claro y oscuro."
    >
      <AppearanceForm />
    </ContentSection>
  )
}
