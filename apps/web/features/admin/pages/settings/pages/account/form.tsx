'use client'

import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'
import { z } from 'zod'

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
import { showSubmittedData } from '@/lib/utils/components/show-submitted-data'

const passwordSchema = z
  .object({
    currentPassword: z.string().trim().min(8, {
      message: 'La contraseña actual debe tener al menos 8 caracteres.',
    }),
    newPassword: z
      .string()
      .trim()
      .min(8, { message: 'La contraseña debe tener al menos 8 caracteres.' })
      .max(50, {
        message: 'La contraseña no puede tener más de 50 caracteres.',
      })
      .regex(/^\S+$/, { message: 'La contraseña no puede contener espacios en blanco.' })
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

type PasswordFormValues = z.infer<typeof passwordSchema>

export function AccountForm() {
  const form = useForm<PasswordFormValues>({
    resolver: zodResolver(passwordSchema),
    defaultValues: { currentPassword: '', newPassword: '', confirmNewPassword: '' },
  })

  const onSubmit = (data: PasswordFormValues) => {
    showSubmittedData(data)
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <div className="space-y-6">
          <FormField
            control={form.control}
            name="currentPassword"
            render={({ field, fieldState }) => (
              <FormItem>
                <FormLabel>Contraseña actual</FormLabel>
                <FormControl>
                  <PasswordInput
                    placeholder="Contraseña actual"
                    autoComplete="current-password"
                    {...field}
                  />
                </FormControl>
                {fieldState.error && <FormMessage />}
              </FormItem>
            )}
          />
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 items-start">
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
                  {fieldState.error && <FormMessage />}
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
                  {fieldState.error && <FormMessage />}
                </FormItem>
              )}
            />
          </div>
        </div>
        <div className="flex justify-center sm:justify-start">
          <Button type="submit">Cambiar contraseña</Button>
        </div>
      </form>
    </Form>
  )
}
