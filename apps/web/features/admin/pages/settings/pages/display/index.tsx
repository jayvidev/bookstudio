import { ContentSection } from '@admin/pages/settings/components/content-section'

import { DisplayForm } from './form'

export function SettingsDisplayPage() {
  return (
    <ContentSection
      title="Visualización"
      desc="Activa o desactiva elementos para controlar lo que se muestra en la aplicación."
    >
      <DisplayForm />
    </ContentSection>
  )
}
