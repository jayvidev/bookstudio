'use client'

import { useState } from 'react'

import { BookOpen } from 'lucide-react'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'

import BookCard from './book-card'

const BOOKS = [
  {
    id: 1,
    title: 'El Quijote',
    author: 'Miguel de Cervantes',
    category: 'Clásico',
    rating: 4.8,
    image: '/images/libros/el-quijote-book-cover.jpg',
  },
  {
    id: 2,
    title: 'Cien años de soledad',
    author: 'Gabriel García Márquez',
    category: 'Realismo Mágico',
    rating: 4.9,
    image: '/images/libros/cien-a-os-de-soledad-book.jpg',
  },
  {
    id: 3,
    title: 'Don Juan',
    author: 'Lord Byron',
    category: 'Poesía',
    rating: 4.6,
    image: '/images/libros/don-juan-poetry-book.jpg',
  },
  {
    id: 4,
    title: 'La Revolución Francesa',
    author: 'Simon Schama',
    category: 'Historia',
    rating: 4.7,
    image: '/images/libros/french-revolution-book.jpg',
  },
  {
    id: 5,
    title: 'El Código Da Vinci',
    author: 'Dan Brown',
    category: 'Misterio',
    rating: 4.5,
    image: '/images/libros/da-vinci-code-mystery.jpg',
  },
  {
    id: 6,
    title: 'Sapiens',
    author: 'Yuval Noah Harari',
    category: 'No Ficción',
    rating: 4.8,
    image: '/images/libros/sapiens-history-book.jpg',
  },
  {
    id: 7,
    title: 'La Bruja de Portobello',
    author: 'Paulo Coelho',
    category: 'Ficción',
    rating: 4.4,
    image: '/images/libros/la-bruja-portobello-book.jpg',
  },
  {
    id: 8,
    title: 'Fundación',
    author: 'Isaac Asimov',
    category: 'Ciencia Ficción',
    rating: 4.7,
    image: '/images/libros/foundation-scifi-book.jpg',
  },
]

export default function BookCatalog({ searchQuery }: { searchQuery: string }) {
  const [selectedCategory, setSelectedCategory] = useState('Todos')
  const [favorites, setFavorites] = useState<number[]>([])

  const categories = [
    'Todos',
    'Clásico',
    'Realismo Mágico',
    'Poesía',
    'Historia',
    'Misterio',
    'No Ficción',
    'Ficción',
    'Ciencia Ficción',
  ]

  const filteredBooks = BOOKS.filter((book) => {
    const matchesCategory = selectedCategory === 'Todos' || book.category === selectedCategory
    const matchesSearch =
      book.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
      book.author.toLowerCase().includes(searchQuery.toLowerCase())
    return matchesCategory && matchesSearch
  })

  const toggleFavorite = (id: number) => {
    setFavorites((prev) => (prev.includes(id) ? prev.filter((fav) => fav !== id) : [...prev, id]))
  }

  return (
    <section id="libros" className="py-20 bg-background">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        {/* Section Header */}
        <div className="mb-12">
          <h2 className="text-3xl sm:text-4xl font-bold text-foreground mb-4">Nuestro Catálogo</h2>
          <p className="text-lg text-foreground/60">
            Descubre miles de libros de todos los géneros y accede al instante
          </p>
        </div>

        {/* Search Bar */}
        <div className="mb-8">
          <div className="relative">
            <Input
              type="search"
              placeholder="Busca libros o autores..."
              className="w-full px-4 py-3 bg-card border-border"
              value={searchQuery}
              disabled
            />
          </div>
        </div>

        {/* Category Filter */}
        <div className="mb-12">
          <div className="flex gap-2 overflow-x-auto pb-4 -mx-4 px-4 sm:mx-0 sm:px-0 sm:flex-wrap">
            {categories.map((category) => (
              <button
                key={category}
                onClick={() => setSelectedCategory(category)}
                className={`whitespace-nowrap px-4 py-2 rounded-lg font-medium transition-colors ${
                  selectedCategory === category
                    ? 'bg-primary text-primary-foreground'
                    : 'bg-muted text-foreground hover:bg-muted/80'
                }`}
              >
                {category}
              </button>
            ))}
          </div>
        </div>

        {/* Books Grid */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {filteredBooks.length > 0 ? (
            filteredBooks.map((book) => (
              <BookCard
                key={book.id}
                book={book}
                isFavorite={favorites.includes(book.id)}
                onToggleFavorite={() => toggleFavorite(book.id)}
              />
            ))
          ) : (
            <div className="col-span-full text-center py-12">
              <BookOpen className="w-12 h-12 text-muted-foreground mx-auto mb-4" />
              <p className="text-foreground/60">No se encontraron libros</p>
            </div>
          )}
        </div>

        {/* Load More */}
        {filteredBooks.length > 0 && (
          <div className="mt-12 text-center">
            <Button variant="outline" className="px-8 py-2">
              Ver más libros
            </Button>
          </div>
        )}
      </div>
    </section>
  )
}
