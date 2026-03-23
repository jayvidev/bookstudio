import { ContentSection } from '@admin/pages/settings/components/content-section'

import { ProfileForm } from './form'

export function SettingsProfilePage() {
  return (
    <ContentSection title="Perfil" desc="Así es como los demás te verán en el sitio.">
      <ProfileForm />
    </ContentSection>
  )
}
