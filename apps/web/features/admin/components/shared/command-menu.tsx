'use client'

import React from 'react'

import { ArrowRight, LaptopMinimal, Moon, Sun } from 'lucide-react'
import { useRouter } from 'next/navigation'
import { useTheme } from 'next-themes'

import { sidebarData } from '@admin/components/sidebar/sidebar.data'

import {
  CommandDialog,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
  CommandSeparator,
} from '@/components/ui/command'
import { ScrollArea } from '@/components/ui/scroll-area'
import { useSearch } from '@/context/search-context'

export function CommandMenu() {
  const router = useRouter()
  const { setTheme } = useTheme()
  const { open, setOpen } = useSearch()

  const runCommand = React.useCallback(
    (command: () => unknown) => {
      setOpen(false)
      command()
    },
    [setOpen]
  )

  return (
    <CommandDialog modal open={open} onOpenChange={setOpen}>
      <CommandInput placeholder="Escribe un comando o busca..." />
      <CommandList>
        <ScrollArea type="hover" className="h-72 pr-1">
          <CommandEmpty>Sin resultados.</CommandEmpty>
          {sidebarData.navGroups.map((group) => (
            <React.Fragment key={group.title}>
              <CommandGroup key={group.title} heading={group.title}>
                {group.items.map((navItem, i) => {
                  const Icon = navItem.icon
                  return (
                    <CommandItem
                      key={`${navItem.url}-${i}`}
                      value={navItem.title}
                      onSelect={() => runCommand(() => router.push(navItem.url))}
                    >
                      <div className="mr-2 flex h-4 w-4 items-center justify-center">
                        <ArrowRight className="text-muted-foreground/80 size-2" />
                      </div>
                      <Icon className="mr-2" />
                      {navItem.title}
                    </CommandItem>
                  )
                })}
              </CommandGroup>
              <CommandSeparator className="my-2" />
            </React.Fragment>
          ))}

          <CommandGroup heading="Tema">
            <CommandItem onSelect={() => runCommand(() => setTheme('light'))}>
              <Sun /> <span>Claro</span>
            </CommandItem>
            <CommandItem onSelect={() => runCommand(() => setTheme('dark'))}>
              <Moon className="scale-90" />
              <span>Oscuro</span>
            </CommandItem>
            <CommandItem onSelect={() => runCommand(() => setTheme('system'))}>
              <LaptopMinimal />
              <span>Sistema</span>
            </CommandItem>
          </CommandGroup>
        </ScrollArea>
      </CommandList>
    </CommandDialog>
  )
}
