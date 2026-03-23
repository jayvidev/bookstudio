import {
  BookCopy,
  BookOpenText,
  Boxes,
  Briefcase,
  Building2,
  Calendar1,
  ChartColumnBig,
  CircleQuestionMark,
  DollarSign,
  Handshake,
  MapPin,
  OctagonAlert,
  Settings,
  ShieldCheck,
  Tags,
  Users,
} from 'lucide-react'

import { pageMap, type ValidUrl } from '@/config/page-map'

type SidebarVisualMeta = {
  icon: React.ElementType
  group: 'principal' | 'biblioteca' | 'usuarios' | 'otros'
}

export const sidebarMap: Partial<Record<ValidUrl, SidebarVisualMeta>> = {}

const setSidebar = <T extends ValidUrl>(url: T, meta: SidebarVisualMeta) => {
  if (pageMap[url].showInSidebar) {
    sidebarMap[url] = meta
  }
}

setSidebar('/admin', { icon: ChartColumnBig, group: 'principal' })
setSidebar('/admin/prestamos', { icon: Handshake, group: 'principal' })
setSidebar('/admin/reservas', { icon: Calendar1, group: 'principal' })
setSidebar('/admin/multas', { icon: OctagonAlert, group: 'principal' })
setSidebar('/admin/pagos', { icon: DollarSign, group: 'principal' })
setSidebar('/admin/ejemplares', { icon: Boxes, group: 'biblioteca' })
setSidebar('/admin/libros', { icon: BookCopy, group: 'biblioteca' })
setSidebar('/admin/autores', { icon: Users, group: 'biblioteca' })
setSidebar('/admin/editoriales', { icon: Building2, group: 'biblioteca' })
setSidebar('/admin/categorias', { icon: Tags, group: 'biblioteca' })
setSidebar('/admin/ubicaciones', { icon: MapPin, group: 'biblioteca' })

setSidebar('/admin/lectores', { icon: BookOpenText, group: 'usuarios' })
setSidebar('/admin/trabajadores', { icon: Briefcase, group: 'usuarios' })
setSidebar('/admin/roles', { icon: ShieldCheck, group: 'usuarios' })

setSidebar('/admin/ajustes', { icon: Settings, group: 'otros' })
setSidebar('/admin/centro-de-ayuda', { icon: CircleQuestionMark, group: 'otros' })
