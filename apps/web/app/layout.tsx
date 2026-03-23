import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import { ThemeProvider } from 'next-themes'
import NextTopLoader from 'nextjs-toploader'

import { ThemeScript } from '@/components/shared/theme-script'
import { Toaster } from '@/components/ui/sonner'

import { QueryProvider } from './providers/query-provider'

import './globals.css'

const inter = Inter({
  subsets: ['latin'],
  variable: '--font-inter',
  display: 'swap',
  weight: '400',
})

export const metadata: Metadata = {
  title: {
    default: 'BookStudio',
    template: '%s - BookStudio',
  },
  description:
    'BookStudio es un sistema de biblioteca para gestionar préstamos, libros, autores y otros recursos, facilitando la administración y seguimiento de los préstamos.',
  appleWebApp: {
    title: 'BookStudio',
    capable: true,
    statusBarStyle: 'default',
  },
  manifest: '/manifest.webmanifest',
  icons: {
    icon: [
      { url: '/icons/favicon-16x16.png', sizes: '16x16', type: 'image/png' },
      { url: '/icons/favicon-32x32.png', sizes: '32x32', type: 'image/png' },
      { url: '/icons/icon.svg', type: 'image/svg+xml' },
    ],
    apple: [{ url: '/icons/apple-touch-icon.png', sizes: '180x180' }],
  },
}

export function generateViewport() {
  return {
    themeColor: [{ color: 'oklch(0.141 0.005 285.823)' }],
  }
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="es" data-scroll-behavior="smooth" suppressHydrationWarning>
      <head>
        <ThemeScript />
      </head>
      <body className={`${inter.className} antialiased`}>
        <QueryProvider>
          <ThemeProvider
            attribute="class"
            defaultTheme="dark"
            disableTransitionOnChange
            enableColorScheme
          >
            <NextTopLoader color="var(--primary)" height={2} easing="linear" showSpinner={false} />
            <Toaster duration={5000} />
            {children}
          </ThemeProvider>
        </QueryProvider>
      </body>
    </html>
  )
}
