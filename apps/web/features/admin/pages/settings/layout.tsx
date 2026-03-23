interface Props {
  children?: React.ReactNode
}

import { Bell, Monitor, Palette, Settings, User, Wrench } from 'lucide-react'

import { SidebarNav } from '@admin/pages/settings/components/sidebar-nav'

import { Separator } from '@/components/ui/separator'

const sidebarNavItems = [
  {
    title: 'Perfil',
    href: '/admin/ajustes',
    icon: <User size={18} />,
  },
  {
    title: 'Cuenta',
    href: '/admin/ajustes/cuenta',
    icon: <Wrench size={18} />,
  },
  {
    title: 'Apariencia',
    href: '/admin/ajustes/apariencia',
    icon: <Palette size={18} />,
  },
  {
    title: 'Notificaciones',
    href: '/admin/ajustes/notificaciones',
    icon: <Bell size={18} />,
  },
  {
    title: 'Visualización',
    href: '/admin/ajustes/visualizacion',
    icon: <Monitor size={18} />,
  },
]

export function SettingsLayout({ children }: Props) {
  return (
    <div data-layout="fixed" className="flex flex-1 p-5 gap-4 flex-col overflow-hidden">
      <div className="flex h-full flex-col">
        <div className="space-y-0.5 flex-none">
          <h1 className="text-2xl font-bold flex items-center gap-2">
            <Settings strokeWidth={2.5} />
            Ajustes
          </h1>
          <p className="text-muted-foreground">
            Administre la configuración y preferencias de su cuenta.
          </p>
        </div>
        <Separator className="my-4 lg:my-6 flex-none" />
        <div className="flex flex-1 flex-col space-y-2 min-h-0 md:space-y-2 lg:flex-row lg:space-y-0 lg:space-x-12">
          <aside className="top-0 lg:sticky lg:w-1/5 flex-none">
            <SidebarNav items={sidebarNavItems} />
          </aside>
          <div className="flex flex-1 min-h-0 w-full p-1">{children}</div>
        </div>
      </div>
    </div>
  )
}
