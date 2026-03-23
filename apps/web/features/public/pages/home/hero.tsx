'use client'

import { ArrowRight, BookMarked } from 'lucide-react'

import { Button } from '@/components/ui/button'

export default function Hero() {
  return (
    <section className="relative overflow-hidden bg-gradient-to-r from-primary/10 via-secondary/5 to-accent/10 pt-20 pb-32 sm:pt-32 sm:pb-40">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
          {/* Left Content */}
          <div className="space-y-8">
            <div className="space-y-4">
              <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full border border-primary/20 bg-primary/5">
                <BookMarked className="w-4 h-4 text-primary" />
                <span className="text-sm text-primary font-medium">Tu biblioteca digital</span>
              </div>
              <h2 className="text-4xl sm:text-5xl font-bold text-foreground text-balance">
                Acceso ilimitado a millones de libros
              </h2>
              <p className="text-lg text-foreground/70">
                Explora nuestro catálogo completo de libros, desde clásicos atemporales hasta los
                lanzamientos más recientes. Lee sin límites en BookStudio.
              </p>
            </div>

            <div className="flex flex-col sm:flex-row gap-4">
              <Button className="gap-2 bg-primary hover:bg-primary/90 text-primary-foreground px-6 py-6 text-base">
                Explorar catálogo <ArrowRight className="w-4 h-4" />
              </Button>
              <Button variant="outline" className="px-6 py-6 text-base">
                Saber más
              </Button>
            </div>

            <div className="flex gap-8 pt-4">
              <div>
                <p className="text-2xl font-bold">50K+</p>
                <p className="text-sm text-foreground/60">Libros disponibles</p>
              </div>
              <div>
                <p className="text-2xl font-bold">100K+</p>
                <p className="text-sm text-foreground/60">Lectores activos</p>
              </div>
              <div>
                <p className="text-2xl font-bold">4.8★</p>
                <p className="text-sm text-foreground/60">Calificación promedio</p>
              </div>
            </div>
          </div>

          {/* Right Image */}
          <div className="hidden md:flex items-center justify-center">
            <div className="relative w-80 h-96">
              <div className="absolute inset-0 bg-gradient-to-br from-primary/20 to-accent/20 rounded-2xl transform -rotate-3"></div>
              <div className="absolute inset-2 bg-gradient-to-br from-secondary/20 to-primary/10 rounded-2xl transform rotate-2 shadow-xl">
                <img
                  src="/images/assets/hero.png"
                  alt="Libros en BookStudio"
                  className="w-full h-full object-cover rounded-2xl"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}
