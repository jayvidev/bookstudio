'use client'

import { useState } from 'react'

import { LogInIcon, Menu, X } from 'lucide-react'
import Link from 'next/link'

import { Logo } from '@/components/shared/logo'
import { Button } from '@/components/ui/button'

export default function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false)

  return (
    <header className="sticky top-0 z-50 w-full border-b border-border bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center gap-2">
            <div className="flex aspect-square size-8 items-center justify-center">
              <Logo />
            </div>
            <h1 className="text-xl font-semibold text-foreground">BookStudio</h1>
          </div>

          <nav className="hidden md:flex items-center gap-8">
            <a
              href="#libros"
              className="text-foreground/80 hover:text-foreground transition-colors"
            >
              Catálogo
            </a>
            <a href="#" className="text-foreground/80 hover:text-foreground transition-colors">
              Géneros
            </a>
            <a href="#" className="text-foreground/80 hover:text-foreground transition-colors">
              Recomendados
            </a>
            <a href="#" className="text-foreground/80 hover:text-foreground transition-colors">
              Contacto
            </a>
          </nav>

          <div className="hidden md:flex items-center gap-3">
            <Button variant="outline">Mi Biblioteca</Button>
            <Link href="/iniciar-sesion">
              <Button className="bg-primary hover:bg-primary/90 text-primary-foreground">
                <LogInIcon />
                Iniciar sesión
              </Button>
            </Link>
          </div>

          <button className="md:hidden" onClick={() => setIsMenuOpen(!isMenuOpen)}>
            {isMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>
        </div>

        {isMenuOpen && (
          <div className="md:hidden pb-4 space-y-3 border-t border-border pt-4">
            <a href="#libros" className="block text-foreground/80 hover:text-foreground py-2">
              Catálogo
            </a>
            <a href="#" className="block text-foreground/80 hover:text-foreground py-2">
              Géneros
            </a>
            <a href="#" className="block text-foreground/80 hover:text-foreground py-2">
              Recomendados
            </a>
            <a href="#" className="block text-foreground/80 hover:text-foreground py-2">
              Contacto
            </a>
            <div className="flex gap-2 pt-4">
              <Button variant="outline" className="flex-1">
                Mi Biblioteca
              </Button>
              <Button className="flex-1 bg-primary text-primary-foreground" asChild>
                <Link href="/iniciar-sesion">Acceder</Link>
              </Button>
            </div>
          </div>
        )}
      </div>
    </header>
  )
}
