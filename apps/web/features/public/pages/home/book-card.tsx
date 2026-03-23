'use client'

import { BookOpen, Calendar, Heart, Star } from 'lucide-react'
import Link from 'next/link'

import { Button } from '@/components/ui/button'

interface Book {
  id: number
  title: string
  author: string
  category: string
  rating: number
  image: string
}

interface BookCardProps {
  book: Book
  isFavorite: boolean
  onToggleFavorite: () => void
}

export default function BookCard({ book, isFavorite, onToggleFavorite }: BookCardProps) {
  return (
    <Link href={`/libro/${book.id}`}>
      <div className="group cursor-pointer">
        <div className="relative overflow-hidden rounded-lg bg-card border border-border hover:border-primary/50 transition-all duration-300">
          {/* Image Container */}
          <div className="relative h-64 overflow-hidden bg-muted">
            <img
              src={book.image || '/placeholder.svg'}
              alt={book.title}
              className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300"
            />

            {/* Favorite Button */}
            <button
              onClick={(e) => {
                e.preventDefault()
                onToggleFavorite()
              }}
              className="absolute top-3 right-3 p-2 rounded-lg bg-background/80 backdrop-blur hover:bg-background transition-colors"
            >
              <Heart
                className={`w-5 h-5 transition-all ${
                  isFavorite ? 'fill-accent text-accent' : 'text-foreground/60'
                }`}
              />
            </button>

            {/* Category Badge */}
            <div className="absolute top-3 left-3 px-3 py-1 rounded-full bg-primary/90 text-primary-foreground text-xs font-medium">
              {book.category}
            </div>
          </div>

          {/* Content */}
          <div className="p-4 space-y-3">
            <div>
              <h3 className="font-bold text-foreground line-clamp-2 group-hover:text-primary transition-colors">
                {book.title}
              </h3>
              <p className="text-sm text-foreground/60">{book.author}</p>
            </div>

            {/* Rating */}
            <div className="flex items-center gap-1">
              <div className="flex gap-0.5">
                {[...Array(5)].map((_, i) => (
                  <Star
                    key={i}
                    className={`size-4 ${
                      i < Math.floor(book.rating)
                        ? 'fill-secondary text-secondary'
                        : 'text-muted-foreground'
                    }`}
                  />
                ))}
              </div>
              <span className="text-xs text-foreground/60 ml-1">{book.rating}</span>
            </div>

            {/* Action Button */}
            <div className="pt-2 space-y-2">
              <Button size="sm" className="w-full gap-2" onClick={(e) => e.preventDefault()}>
                <BookOpen className="size-4" />
                <span>Leer</span>
              </Button>
              <Button
                size="sm"
                variant="outline"
                className="w-full gap-2"
                onClick={(e) => e.preventDefault()}
              >
                <Calendar className="size-4" />
                <span>Reservar</span>
              </Button>
            </div>
          </div>
        </div>
      </div>
    </Link>
  )
}
