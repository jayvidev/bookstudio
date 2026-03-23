'use client'

import { useState } from 'react'

import BookCatalog from './book-catalog'
import Hero from './hero'

export function HomePage() {
  const [searchQuery] = useState('')

  return (
    <>
      <Hero />
      <BookCatalog searchQuery={searchQuery} />
    </>
  )
}
