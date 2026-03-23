'use client'

import { zodResolver } from '@hookform/resolvers/zod'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import { useForm } from 'react-hook-form'
import { z } from 'zod'

import { Logo } from '@/components/shared/logo'
import { Button } from '@/components/ui/button'
import { Form, FormControl, FormField, FormItem, FormLabel } from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { PasswordInput } from '@/components/ui/password-input'
import { cn } from '@/lib/utils'

const loginSchema = z.object({
  email: z.email().trim(),
  password: z.string().trim().min(8),
})

type LoginFormValues = z.infer<typeof loginSchema>

export function LoginPage({ className, ...props }: React.ComponentPropsWithoutRef<'div'>) {
  const router = useRouter()

  const form = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
    mode: 'onChange',
    defaultValues: {
      email: '',
      password: '',
    },
  })

  const onSubmit = () => {
    router.push('/admin')
  }

  return (
    <div className={cn('flex flex-col gap-6', className)} {...props}>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)}>
          <div className="flex flex-col gap-6">
            <div className="flex flex-col items-center gap-2">
              <div className="flex flex-col items-center gap-2 font-medium">
                <Logo className="size-10" />
                <span className="sr-only">BookStudio</span>
              </div>
              <h1 className="text-xl font-bold">¡Bienvenido a BookStudio!</h1>
              <div className="text-center text-sm">
                Inicia sesión y empieza a gestionar tus préstamos.
              </div>
            </div>

            <div className="flex flex-col gap-6">
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Correo electrónico</FormLabel>
                    <FormControl>
                      <Input
                        type="email"
                        placeholder="correo@ejemplo.com"
                        autoComplete="email"
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <div className="flex items-center justify-between">
                      <FormLabel>Contraseña</FormLabel>
                      <Link
                        href="/recuperar-contrasena"
                        className="text-sm underline-offset-4 hover:underline"
                      >
                        ¿Olvidaste tu contraseña?
                      </Link>
                    </div>
                    <FormControl>
                      <PasswordInput
                        placeholder="Contraseña"
                        autoComplete="current-password"
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />

              <Button type="submit" className="w-full">
                Iniciar sesión
              </Button>
            </div>
          </div>
        </form>
      </Form>

      <div className="text-balance text-center text-xs text-muted-foreground [&_a]:underline [&_a]:underline-offset-4 hover:[&_a]:text-primary">
        Al hacer clic en continuar, acepta nuestros <a href="#">Términos de servicio</a> y{' '}
        <a href="#">Política de privacidad</a>.
      </div>
    </div>
  )
}
