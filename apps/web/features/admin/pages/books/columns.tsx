'use client'

import type { ColumnDef } from '@tanstack/react-table'
import { BookCheck, BookText, BookX, Building2, CircleCheck, Tags, XCircle } from 'lucide-react'

import { statusBadges } from '@admin/components/badges'
import { DataTableRowActions } from '@admin/components/data-table/data-table-row-actions'

import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { Badge } from '@/components/ui/badge'
import { Checkbox } from '@/components/ui/checkbox'
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip'
import type { BookFilterOptionsParams } from '@/lib/schemas/book/book.filter.options.schema'
import type { BookList } from '@/lib/schemas/book/book.list.schema'
import { withMetaLabelFilter } from '@/lib/utils/components/with-meta-label-filter'
import { withMetaLabelHeader } from '@/lib/utils/components/with-meta-label-header'

import { availabilityOptions, loanOptions, statusOptions } from './filter-options'

export const getColumns = (options?: BookFilterOptionsParams): ColumnDef<BookList>[] => {
  const {
    categories: categoriesOptions = [],
    publishers: publishersOptions = [],
    languages: languagesOptions = [],
  } = options ?? {}

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
      accessorKey: 'title',
      header: withMetaLabelHeader<BookList>(),
      cell: ({ getValue, row }) => {
        const title = getValue<string>()
        const coverUrl = row.original.coverUrl

        return (
          <div className="flex items-center gap-2">
            <Avatar className="rounded-sm">
              {coverUrl ? (
                <AvatarImage src={coverUrl} alt={title} className="object-cover" />
              ) : (
                <AvatarFallback className="text-xs object-cover rounded-sm">
                  <BookText className="size-5 text-muted-foreground" />
                </AvatarFallback>
              )}
            </Avatar>
            <span className="truncate max-w-[14rem] text-sm leading-snug break-words">{title}</span>
          </div>
        )
      },
      meta: {
        searchable: true,
      },
    },
    {
      id: 'category',
      accessorFn: (row) => String(row.category.id),
      header: withMetaLabelHeader<BookList>(),
      cell: ({ row }) => {
        const name = row.original.category.name
        return (
          <Badge variant="outline">
            <Tags className="mr-1" />
            {name}
          </Badge>
        )
      },
      meta: withMetaLabelFilter<BookList>({
        columnId: 'category',
        options: categoriesOptions ?? [],
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      id: 'publisher',
      accessorFn: (row) => String(row.publisher.id),
      header: withMetaLabelHeader<BookList>(),
      cell: ({ row }) => {
        const name = row.original.publisher.name
        return (
          <Badge variant="outline">
            <Building2 className="mr-1" />
            {name}
          </Badge>
        )
      },
      meta: withMetaLabelFilter<BookList>({
        columnId: 'publisher',
        options: publishersOptions ?? [],
      }),
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      id: 'language',
      accessorFn: (row) => String(row.language.id),
      header: withMetaLabelHeader<BookList>(),
      cell: ({ row }) => {
        const code = row.original.language.code
        const name = row.original.language.name

        return (
          <TooltipProvider>
            <Tooltip>
              <TooltipTrigger asChild>
                <Badge variant="outline">
                  <img
                    src={`https://flagcdn.com/w20/${code}.jpg`}
                    alt={name}
                    className="w-4 h-auto mr-1"
                  />
                  {code.toUpperCase()}
                </Badge>
              </TooltipTrigger>
              <TooltipContent side="top">{name}</TooltipContent>
            </Tooltip>
          </TooltipProvider>
        )
      },
      enableSorting: false,
      meta: {
        headerClass: 'text-center',
        cellClass: 'text-center',
        ...withMetaLabelFilter<BookList>({
          columnId: 'language',
          options: languagesOptions ?? [],
        }),
      },
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      id: 'copiesLoaned',
      accessorFn: (row) => (row.copies.loaned > 0 ? 'loaned' : 'notLoaned'),
      header: withMetaLabelHeader<BookList>(),
      cell: ({ row }) => {
        const value = row.original.copies.loaned
        const isEmpty = value === 0
        const Icon = isEmpty ? BookX : BookCheck

        return (
          <TooltipProvider>
            <Tooltip>
              <TooltipTrigger asChild>
                <Badge variant="outline">
                  <Icon
                    className={`mr-1 ${isEmpty ? 'text-zinc-500 dark:text-zinc-400' : 'text-blue-500 dark:text-blue-400'}`}
                  />
                  {isEmpty ? '–' : value}
                </Badge>
              </TooltipTrigger>
              <TooltipContent side="top">
                {isEmpty
                  ? 'Sin ejemplares prestados'
                  : `${value} ejemplar${value === 1 ? '' : 'es'} prestado${value === 1 ? '' : 's'}`}
              </TooltipContent>
            </Tooltip>
          </TooltipProvider>
        )
      },
      meta: {
        headerClass: 'text-center',
        cellClass: 'text-center',
        ...withMetaLabelFilter<BookList>({
          columnId: 'copiesLoaned',
          options: loanOptions,
        }),
      },
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      id: 'copiesAvailable',
      accessorFn: (row) => (row.copies.available > 0 ? 'available' : 'notAvailable'),
      header: withMetaLabelHeader<BookList>(),
      cell: ({ row }) => {
        const value = row.original.copies.available
        const isEmpty = value === 0
        const Icon = isEmpty ? XCircle : CircleCheck

        return (
          <TooltipProvider>
            <Tooltip>
              <TooltipTrigger asChild>
                <Badge variant="outline">
                  <Icon
                    className={`mr-1 ${isEmpty ? 'text-destructive' : 'text-green-500 dark:text-green-400'}`}
                  />
                  {isEmpty ? '–' : value}
                </Badge>
              </TooltipTrigger>
              <TooltipContent side="top">
                {isEmpty
                  ? 'Sin ejemplares disponibles'
                  : `${value} ejemplar${value === 1 ? '' : 'es'} disponible${value === 1 ? '' : 's'}`}
              </TooltipContent>
            </Tooltip>
          </TooltipProvider>
        )
      },
      meta: {
        headerClass: 'text-center',
        cellClass: 'text-center',
        ...withMetaLabelFilter<BookList>({
          columnId: 'copiesAvailable',
          options: availabilityOptions,
        }),
      },
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      accessorKey: 'status',
      header: withMetaLabelHeader<BookList>(),
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
        ...withMetaLabelFilter<BookList>({
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
