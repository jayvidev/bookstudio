import { sidebarData } from '@admin/components/sidebar/sidebar.data'
import { sidebarMap } from '@admin/components/sidebar/sidebar-map'

import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from '@/components/ui/breadcrumb'
import { pageMap } from '@/config/page-map'

interface BreadcrumbsProps {
  pathname: string
}

export function Breadcrumbs({ pathname }: BreadcrumbsProps) {
  const currentPage = pageMap[pathname]
  if (!currentPage) return null

  const groupKey = sidebarMap[pathname]?.group
  const groupName = sidebarData.navGroups.find((g) => g.title.toLowerCase() === groupKey)?.title

  return (
    <Breadcrumb>
      <BreadcrumbList>
        {groupName && (
          <BreadcrumbItem>
            <span className="text-muted-foreground cursor-default">{groupName}</span>
          </BreadcrumbItem>
        )}
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>{currentPage.title}</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  )
}
