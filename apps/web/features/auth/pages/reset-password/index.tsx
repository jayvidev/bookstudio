'use client'

import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'
import { z } from 'zod'

import { Logo } from '@/components/shared/logo'
import { Button } from '@/components/ui/button'
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form'
import { PasswordInput } from '@/components/ui/password-input'
import { cn } from '@/lib/utils'

const resetPasswordSchema = z
  .object({
    newPassword: z
      .string()
      .trim()
      .min(8, { message: 'La contraseña debe tener al menos 8 caracteres.' })
      .max(50, {
        message: 'La contraseña no puede tener más de 50 caracteres.',
      })
      .regex(/^\S+$/, {
        message: 'La contraseña no puede contener espacios en blanco.',
      })
      .regex(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/, {
        message:
          'La contraseña debe incluir al menos una minúscula, una mayúscula, un número y un carácter especial (@$!%*?&).',
      }),
    confirmNewPassword: z
      .string()
      .trim()
      .min(8, { message: 'Debes confirmar la contraseña nueva.' }),
  })
  .refine((data) => data.newPassword === data.confirmNewPassword, {
    message: 'Las contraseñas no coinciden.',
    path: ['confirmNewPassword'],
  })

type ResetPasswordFormValues = z.infer<typeof resetPasswordSchema>

export function ResetPasswordPage({ className, ...props }: React.ComponentPropsWithoutRef<'div'>) {
  const form = useForm<ResetPasswordFormValues>({
    resolver: zodResolver(resetPasswordSchema),
    mode: 'onChange',
    defaultValues: {
      newPassword: '',
      confirmNewPassword: '',
    },
  })

  const onSubmit = () => {}

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
              <h1 className="text-xl font-bold">Crear contraseña nueva</h1>
              <div className="text-center text-sm">
                Ingresa la contraseña nueva para tu cuenta de BookStudio a continuación.
              </div>
            </div>
            <div className="flex flex-col gap-6">
              <FormField
                control={form.control}
                name="newPassword"
                render={({ field, fieldState }) => (
                  <FormItem>
                    <FormLabel>Contraseña nueva</FormLabel>
                    <FormControl>
                      <PasswordInput
                        placeholder="Contraseña nueva"
                        autoComplete="new-password"
                        {...field}
                      />
                    </FormControl>
                    {fieldState.error && <FormMessage>{fieldState.error.message}</FormMessage>}
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="confirmNewPassword"
                render={({ field, fieldState }) => (
                  <FormItem>
                    <FormLabel>Confirmar contraseña nueva</FormLabel>
                    <FormControl>
                      <PasswordInput
                        placeholder="Confirmar contraseña nueva"
                        autoComplete="new-password"
                        {...field}
                      />
                    </FormControl>
                    {fieldState.error && <FormMessage>{fieldState.error.message}</FormMessage>}
                  </FormItem>
                )}
              />
              <Button type="submit">Crear contraseña</Button>
            </div>
          </div>
        </form>
      </Form>
    </div>
  )
}
