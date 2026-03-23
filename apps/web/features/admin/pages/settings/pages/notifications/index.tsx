import { ContentSection } from '@admin/pages/settings/components/content-section'

import { NotificationsForm } from './form'

export function SettingsNotificationsPage() {
  return (
    <ContentSection title="Notificaciones" desc="Configura cómo recibes notificaciones.">
      <NotificationsForm />
    </ContentSection>
  )
}
