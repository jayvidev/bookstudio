'use client'

import { useEffect } from 'react'

import { Moon, Sun } from 'lucide-react'
import { useTheme } from 'next-themes'

import { Button } from '@/components/ui/button'

export function ThemeToggle() {
  const { setTheme, resolvedTheme } = useTheme()

  useEffect(() => {
    const themeColor = resolvedTheme === 'dark' ? 'oklch(0.141 0.005 285.823)' : 'oklch(1 0 0)'
    const metaThemeColor = document.querySelector("meta[name='theme-color']")
    if (metaThemeColor) metaThemeColor.setAttribute('content', themeColor)
  }, [resolvedTheme])

  return (
    <Button
      variant="ghost"
      size="icon"
      className="rounded-full relative [&>svg]:!transition-all"
      onClick={() => setTheme(resolvedTheme === 'light' ? 'dark' : 'light')}
    >
      <Sun className="rotate-0 scale-100 opacity-100 dark:rotate-90 dark:scale-0 dark:opacity-0" />
      <Moon className="absolute rotate-90 scale-0 opacity-0 dark:rotate-0 dark:scale-100 dark:opacity-100" />
      <span className="sr-only">Cambiar tema</span>
    </Button>
  )
}
