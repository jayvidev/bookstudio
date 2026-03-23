'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { Building2, MapPin } from 'lucide-react'

import { statusBadges } from '@admin/components/badges'
import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import type { PublisherFilterOptionsParams } from '@/lib/schemas/publisher/publisher.filter.options.schema'
import type { PublisherList } from '@/lib/schemas/publisher/publisher.list.schema'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

import { statusOptions } from './filter-options'

export const getColumns = (options?: PublisherFilterOptionsParams): ColumnDef<PublisherList>[] => {
  const { nationalities: nationalitiesOptions = [] } = options ?? {}

  return [
    {
      id: 'select',
      header: ({ table }) => (
        <Checkbox
          checked={
            table.getIsAllPageRowsSelected() ||
            (table.getIsSomePageRowsSelected() && 'indeterminate')
          }
          onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
          aria-label="Seleccionar todo"
          className="translate-y-0.5"
        />
      ),
      cell: ({ row }) => (
        <Checkbox
          checked={row.getIsSelected()}
          onCheckedChange={(value) => row.toggleSelected(!!value)}
          aria-label="Seleccionar fila"
          className="translate-y-0.5"
        />
      ),
      enableSorting: false,
      enableHiding: false,
    },
    {
      accessorKey: 'name',
      header: withMetaLabelHeader<PublisherList>(),
      cell: ({ getValue, row }) => {
        const name = getValue<string>()
        const photoUrl = row.original.photoUrl

        return (
          <div className="flex items-center gap-2">
            <Avatar className="rounded-sm">
              {photoUrl ? (
                <AvatarImage src={photoUrl} alt={name} className="object-cover" />
              ) : (
                <AvatarFallback className="text-xs object-cover rounded-sm">
                  <Building2 className="size-5 text-muted-foreground" />
                </AvatarFallback>
              )}
            </Avatar>
            <span className="truncate max-w-[14rem] text-sm leading-snug break-words">{name}</span>
          </div>
        )
      },
      meta: {
        searchable: true,
      },
    },
    {
      id: 'nationality',
      accessorFn: (row) => String(row.nationality.id),
      header: withMetaLabelHeader<PublisherList>(),
      cell: ({ row }) => {
        const code = row.original.nationality.code
        const name = row.original.nationality.name

        return (
          <Badge variant="outline">
            <img
              src={`https://flagcdn.com/w20/${code}.jpg`}
              alt={name}
              className="w-4 h-auto mr-1"
            />
            {name}
          </Badge>
        )
      },
      enableSorting: false,
      meta: withMetaLabelFilter<PublisherList>({
        columnId: 'nationality',
        options: nationalitiesOptions,
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      accessorKey: 'website',
      header: withMetaLabelHeader<PublisherList>(),
      cell: ({ getValue }) => {
        const website = getValue<string>()
        if (!website) return null

        const url = /^https?:\/\//i.test(website) ? website : `https://${website}`

        return (
          <Badge variant="outline" className="flex items-center gap-1">
            <a href={url} target="_blank" rel="noopener noreferrer" className="hover:underline">
              {website}
            </a>
          </Badge>
        )
      },
      enableSorting: false,
    },
    {
      accessorKey: 'address',
      header: withMetaLabelHeader<PublisherList>(),
      cell: ({ getValue }) => {
        const address = getValue<string>()
        return (
          <Badge variant="outline">
            <MapPin className="mr-1" />
            {address}
          </Badge>
        )
      },
      enableSorting: false,
    },
    {
      accessorKey: 'status',
      header: withMetaLabelHeader<PublisherList>(),
      cell: ({ row }) => {
        const meta = statusBadges[row.original.status]

        if (!meta) return null
        const Icon = meta.icon

        return (
          <Badge variant={meta.variant}>
            <Icon className="mr-1" />
            {meta.label}
          </Badge>
        )
      },
      enableSorting: false,
      meta: {
        headerClass: 'text-center',
        cellClass: 'text-center',
        ...withMetaLabelFilter<PublisherList>({
          columnId: 'status',
          options: statusOptions,
        }),
      },
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      id: 'actions',
      cell: () => <DataTableRowActions />,
    },
  ]
}
