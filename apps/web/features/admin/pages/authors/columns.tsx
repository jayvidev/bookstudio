'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { Calendar, User } from 'lucide-react'

import { statusBadges } from '@admin/components/badges'
import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import type { AuthorFilterOptionsParams } from '@/lib/schemas/author/author.filter.options.schema'
import type { AuthorList } from '@/lib/schemas/author/author.list.schema'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

import { statusOptions } from './filter-options'

export const getColumns = (options?: AuthorFilterOptionsParams): ColumnDef<AuthorList>[] => {
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
      header: withMetaLabelHeader<AuthorList>(),
      cell: ({ getValue, row }) => {
        const name = getValue<string>()
        const photoUrl = row.original.photoUrl

        return (
          <div className="flex items-center gap-2">
            <Avatar className="rounded-sm">
              {photoUrl ? (
                <AvatarImage src={photoUrl} alt={`Foto de ${name}`} className="object-cover" />
              ) : (
                <AvatarFallback className="text-xs object-cover rounded-sm">
                  <User className="size-5 text-muted-foreground" />
                </AvatarFallback>
              )}
            </Avatar>
            <span>{name}</span>
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
      header: withMetaLabelHeader<AuthorList>(),
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
      meta: withMetaLabelFilter<AuthorList>({
        columnId: 'nationality',
        options: nationalitiesOptions,
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      accessorKey: 'birthDate',
      header: withMetaLabelHeader<AuthorList>(),
      meta: {
        dateRangeFilter: true,
        headerClass: 'text-center',
        cellClass: 'text-center',
      },
      cell: ({ getValue }) => {
        const birthDate = new Date(getValue<string>())
        const formatted = new Intl.DateTimeFormat('es-ES', {
          day: '2-digit',
          month: 'short',
          year: 'numeric',
        })
          .format(birthDate)
          .replace('.', '')

        return (
          <Badge variant="outline">
            <Calendar className="mr-1" />
            {formatted}
          </Badge>
        )
      },
      filterFn: (row, id, value) => {
        const rowDate = new Date(row.getValue(id))
        const [startDate, endDate] = value
        return rowDate >= startDate && rowDate <= endDate
      },
    },
    {
      accessorKey: 'status',
      header: withMetaLabelHeader<AuthorList>(),
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
        ...withMetaLabelFilter<AuthorList>({
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
