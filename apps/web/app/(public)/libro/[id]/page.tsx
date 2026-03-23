'use client'

import { ArrowLeft, BookOpen, Calendar, Heart, Share2, Star } from 'lucide-react'
import Link from 'next/link'
import { useParams } from 'next/navigation'

import { Button } from '@/components/ui/button'

const BOOKS = [
  {
    id: 1,
    title: 'El Quijote',
    author: 'Miguel de Cervantes',
    category: 'Clásico',
    rating: 4.8,
    image: '/images/libros/el-quijote-book-cover.jpg',
    description:
      'La novela de caballerías más famosa de todos los tiempos. Don Quijote de la Mancha es una obra maestra de la literatura universal que narra las aventuras de un hidalgo que pierde la razón por leer libros de caballerías.',
    year: 1605,
    pages: 1072,
    language: 'Español',
    reviews: 2543,
  },
  {
    id: 2,
    title: 'Cien años de soledad',
    author: 'Gabriel García Márquez',
    category: 'Realismo Mágico',
    rating: 4.9,
    image: '/images/libros/cien-a-os-de-soledad-book.jpg',
    description:
      'Una obra maestra del realismo mágico que cuenta la historia de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo.',
    year: 1967,
    pages: 471,
    language: 'Español',
    reviews: 1876,
  },
  {
    id: 3,
    title: 'Don Juan',
    author: 'Lord Byron',
    category: 'Poesía',
    rating: 4.6,
    image: '/images/libros/don-juan-poetry-book.jpg',
    description:
      'Un poema épico que relata las aventuras del legendario seductor Don Juan a través de diferentes países y culturas.',
    year: 1819,
    pages: 856,
    language: 'Inglés',
    reviews: 654,
  },
  {
    id: 4,
    title: 'La Revolución Francesa',
    author: 'Simon Schama',
    category: 'Historia',
    rating: 4.7,
    image: '/images/libros/french-revolution-book.jpg',
    description:
      'Un análisis profundo y cautivador de los eventos que transformaron Francia y el mundo durante el período revolucionario.',
    year: 1989,
    pages: 936,
    language: 'Inglés',
    reviews: 432,
  },
  {
    id: 5,
    title: 'El Código Da Vinci',
    author: 'Dan Brown',
    category: 'Misterio',
    rating: 4.5,
    image: '/images/libros/da-vinci-code-mystery.jpg',
    description:
      'Un thriller que combina el arte, la historia y el misterio en una trama emocionante que mantiene al lector en vilo.',
    year: 2003,
    pages: 689,
    language: 'Inglés',
    reviews: 3421,
  },
  {
    id: 6,
    title: 'Sapiens',
    author: 'Yuval Noah Harari',
    category: 'No Ficción',
    rating: 4.8,
    image: '/images/libros/sapiens-history-book.jpg',
    description:
      'Una historia de la humanidad desde el Homo sapiens hasta la era moderna, explorando cómo nuestras especies llegó a dominar el mundo.',
    year: 2011,
    pages: 528,
    language: 'Inglés',
    reviews: 2154,
  },
  {
    id: 7,
    title: 'La Bruja de Portobello',
    author: 'Paulo Coelho',
    category: 'Ficción',
    rating: 4.4,
    image: '/images/libros/la-bruja-portobello-book.jpg',
    description:
      'Una novela que explora la vida de una mujer misteriosa y sus múltiples interpretaciones a través de los testimonios de quienes la conocieron.',
    year: 2006,
    pages: 260,
    language: 'Español',
    reviews: 987,
  },
  {
    id: 8,
    title: 'Fundación',
    author: 'Isaac Asimov',
    category: 'Ciencia Ficción',
    rating: 4.7,
    image: '/images/libros/foundation-scifi-book.jpg',
    description:
      'El primer libro de la serie Fundación, una epopeya de ciencia ficción que imagina el futuro lejano de la humanidad y sus civilizaciones galácticas.',
    year: 1951,
    pages: 544,
    language: 'Inglés',
    reviews: 1567,
  },
]

export default function BookDetailPage() {
  const params = useParams()
  const bookId = parseInt(params.id as string)
  const book = BOOKS.find((b) => b.id === bookId)

  if (!book) {
    return (
      <div className="min-h-screen bg-background flex items-center justify-center">
        <div className="text-center">
          <h1 className="text-2xl font-bold text-foreground mb-4">Libro no encontrado</h1>
          <Link href="/">
            <Button>Volver al catálogo</Button>
          </Link>
        </div>
      </div>
    )
  }

  return (
    <main className="min-h-screen bg-background">
      {/* Header */}
      <div className="bg-card border-b border-border">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <Link href="/#libros">
            <Button variant="ghost" className="gap-2 mb-4">
              <ArrowLeft className="w-4 h-4" />
              Volver al catálogo
            </Button>
          </Link>
        </div>
      </div>

      {/* Book Details */}
      <section className="py-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-12">
            {/* Book Cover */}
            <div className="md:col-span-1">
              <div className="sticky top-6 space-y-4">
                <div className="aspect-video md:aspect-square overflow-hidden rounded-lg bg-muted">
                  <img
                    src={book.image || '/placeholder.svg'}
                    alt={book.title}
                    className="w-full h-full object-cover"
                  />
                </div>
                <div className="space-y-2">
                  <Button className="w-full gap-2" size="lg">
                    <BookOpen className="size-4" />
                    Leer Ahora
                  </Button>
                  <Button variant="outline" className="w-full gap-2" size="lg">
                    <Calendar className="size-4" />
                    Reservar
                  </Button>
                  <div className="flex gap-2">
                    <Button variant="outline" className="flex-1 gap-2">
                      <Heart className="size-4" />
                      Favorito
                    </Button>
                    <Button variant="outline" className="flex-1 gap-2">
                      <Share2 className="size-4" />
                      Compartir
                    </Button>
                  </div>
                </div>
              </div>
            </div>

            {/* Book Info */}
            <div className="md:col-span-2 space-y-6">
              {/* Title and Author */}
              <div>
                <div className="inline-block px-3 py-1 rounded-full bg-primary/10 text-primary text-sm font-medium mb-3">
                  {book.category}
                </div>
                <h1 className="text-4xl sm:text-5xl font-bold text-foreground mb-2">
                  {book.title}
                </h1>
                <p className="text-xl text-foreground/60">por {book.author}</p>
              </div>

              {/* Rating */}
              <div className="flex items-center gap-4">
                <div className="flex items-center gap-2">
                  <div className="flex gap-1">
                    {[...Array(5)].map((_, i) => (
                      <Star
                        key={i}
                        className={`w-5 h-5 ${
                          i < Math.floor(book.rating)
                            ? 'fill-secondary text-secondary'
                            : 'text-muted-foreground'
                        }`}
                      />
                    ))}
                  </div>
                  <span className="text-lg font-semibold text-foreground">{book.rating}</span>
                </div>
                <span className="text-foreground/60">({book.reviews} reseñas)</span>
              </div>

              {/* Description */}
              <div className="space-y-2">
                <h2 className="text-2xl font-bold text-foreground">Descripción</h2>
                <p className="text-lg text-foreground/70 leading-relaxed">{book.description}</p>
              </div>

              {/* Book Metadata */}
              <div className="grid grid-cols-2 md:grid-cols-4 gap-4 pt-6 border-t border-border">
                <div>
                  <p className="text-sm text-foreground/60 mb-1">Año de publicación</p>
                  <p className="text-lg font-semibold text-foreground">{book.year}</p>
                </div>
                <div>
                  <p className="text-sm text-foreground/60 mb-1">Páginas</p>
                  <p className="text-lg font-semibold text-foreground">{book.pages}</p>
                </div>
                <div>
                  <p className="text-sm text-foreground/60 mb-1">Idioma</p>
                  <p className="text-lg font-semibold text-foreground">{book.language}</p>
                </div>
                <div>
                  <p className="text-sm text-foreground/60 mb-1">Formato</p>
                  <p className="text-lg font-semibold text-foreground">Digital</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  )
}
