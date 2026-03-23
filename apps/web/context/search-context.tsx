'use client'

import React from 'react'

import { CommandMenu } from '@admin/components/shared/command-menu'

interface SearchContextType {
  open: boolean
  setOpen: React.Dispatch<React.SetStateAction<boolean>>
}

const SearchContext = React.createContext<SearchContextType | null>(null)

interface Props {
  children: React.ReactNode
}

export function SearchProvider({ children }: Props) {
  const [open, setOpen] = React.useState(false)

  React.useEffect(() => {
    const down = (e: KeyboardEvent) => {
      if (e.key === 'k' && (e.metaKey || e.ctrlKey)) {
        e.preventDefault()
        setOpen((open) => !open)
      }
    }
    document.addEventListener('keydown', down)
    return () => document.removeEventListener('keydown', down)
  }, [])

  return (
    <SearchContext.Provider value={{ open, setOpen }}>
      {children}
      <CommandMenu />
    </SearchContext.Provider>
  )
}

export const useSearch = () => {
  const context = React.useContext(SearchContext)

  if (!context) {
    if (process.env.NODE_ENV === 'development') {
      console.warn('[useSearch] called outside of <SearchProvider> during hot reload')

      return {
        open: false,
        setOpen: () => {
          console.warn('[useSearch] setOpen was called without context (ignored)')
        },
      }
    }

    throw new Error('useSearch must be used within a <SearchProvider>')
  }

  return context
}
