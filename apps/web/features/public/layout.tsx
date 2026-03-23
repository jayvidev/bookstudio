import Footer from './components/footer'
import Header from './components/header'

interface Props {
  children?: React.ReactNode
}

export function PublicLayout({ children }: Props) {
  return (
    <div className="min-h-screen bg-background">
      <Header />
      <main>{children}</main>
      <Footer />
    </div>
  )
}
