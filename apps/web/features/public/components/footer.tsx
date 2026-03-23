'use client'

import { Mail, MapPin, Phone } from 'lucide-react'

import { Logo } from '@/components/shared/logo'

export default function Footer() {
  return (
    <footer className="bg-foreground/5 border-t border-border">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-8">
          <div className="space-y-4">
            <div className="flex items-center gap-2">
              <div className="flex aspect-square size-8 items-center justify-center">
                <Logo />
              </div>
              <span className="font-bold text-lg text-foreground">BookStudio</span>
            </div>
            <p className="text-sm text-foreground/60">
              Tu biblioteca digital para descubrir y compartir historias increíbles.
            </p>
          </div>

          <div className="space-y-3">
            <h4 className="font-semibold text-foreground">Catálogo</h4>
            <ul className="space-y-2 text-sm">
              <li>
                <a href="#" className="text-foreground/60 hover:text-foreground transition-colors">
                  Todos los libros
                </a>
              </li>
              <li>
                <a href="#" className="text-foreground/60 hover:text-foreground transition-colors">
                  Recomendados
                </a>
              </li>
              <li>
                <a href="#" className="text-foreground/60 hover:text-foreground transition-colors">
                  Bestsellers
                </a>
              </li>
              <li>
                <a href="#" className="text-foreground/60 hover:text-foreground transition-colors">
                  Novedades
                </a>
              </li>
            </ul>
          </div>

          <div className="space-y-3">
            <h4 className="font-semibold text-foreground">Empresa</h4>
            <ul className="space-y-2 text-sm">
              <li>
                <a href="#" className="text-foreground/60 hover:text-foreground transition-colors">
                  Sobre nosotros
                </a>
              </li>
              <li>
                <a href="#" className="text-foreground/60 hover:text-foreground transition-colors">
                  Blog
                </a>
              </li>
              <li>
                <a href="#" className="text-foreground/60 hover:text-foreground transition-colors">
                  Empleos
                </a>
              </li>
              <li>
                <a href="#" className="text-foreground/60 hover:text-foreground transition-colors">
                  Prensa
                </a>
              </li>
            </ul>
          </div>

          <div className="space-y-3">
            <h4 className="font-semibold text-foreground">Contacto</h4>
            <ul className="space-y-2 text-sm">
              <li className="flex gap-2 text-foreground/60">
                <Mail className="w-4 h-4 mt-0.5" />
                <span>info@bookstudio.com</span>
              </li>
              <li className="flex gap-2 text-foreground/60">
                <Phone className="w-4 h-4 mt-0.5" />
                <span>+34 123 456 789</span>
              </li>
              <li className="flex gap-2 text-foreground/60">
                <MapPin className="w-4 h-4 mt-0.5" />
                <span>Madrid, España</span>
              </li>
            </ul>
          </div>
        </div>

        <div className="border-t border-border pt-8">
          <div className="flex flex-col md:flex-row items-center justify-between gap-4">
            <p className="text-sm text-foreground/60">
              © 2025 BookStudio. Todos los derechos reservados.
            </p>
            <div className="flex gap-6 text-sm text-foreground/60">
              <a href="#" className="hover:text-foreground transition-colors">
                Privacidad
              </a>
              <a href="#" className="hover:text-foreground transition-colors">
                Términos
              </a>
              <a href="#" className="hover:text-foreground transition-colors">
                Cookies
              </a>
            </div>
          </div>
        </div>
      </div>
    </footer>
  )
}
