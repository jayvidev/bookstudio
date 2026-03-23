import { pageMap, type ValidUrl } from '@/config/page-map'

import { sidebarMap } from './sidebar-map'

type ValidPagePath = ValidUrl

type GroupKey = 'principal' | 'biblioteca' | 'usuarios' | 'otros'

const getSidebarItemsByGroup = (group: GroupKey) =>
  (Object.keys(pageMap) as ValidPagePath[])
    .filter((url) => pageMap[url].showInSidebar && sidebarMap[url]?.group === group)
    .map((url) => ({
      title: pageMap[url].title,
      url,
      icon: sidebarMap[url]!.icon,
    }))

export const sidebarData = {
  user: {
    name: 'Jason',
    email: 'jason.vilac@gmail.com',
    avatar: '',
  },
  navGroups: [
    {
      title: 'Principal',
      items: getSidebarItemsByGroup('principal'),
    },
    {
      title: 'Biblioteca',
      items: getSidebarItemsByGroup('biblioteca'),
    },
    {
      title: 'Usuarios',
      items: getSidebarItemsByGroup('usuarios'),
    },
    {
      title: 'Otros',
      items: getSidebarItemsByGroup('otros'),
    },
  ],
}
