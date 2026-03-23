'use client'

import { useEffect, useState } from 'react'

import { zodResolver } from '@hookform/resolvers/zod'
import { Moon, Sun } from 'lucide-react'
import { useTheme } from 'next-themes'
import { useForm } from 'react-hook-form'
import { z } from 'zod'

import { Button } from '@/components/ui/button'
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form'
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group'
import { showSubmittedData } from '@/lib/utils/components/show-submitted-data'

const schema = z.object({
  theme: z.enum(['light', 'dark']),
})

type AppearanceFormValues = z.infer<typeof schema>

export function AppearanceForm() {
  const { resolvedTheme, setTheme } = useTheme()
  const [mounted, setMounted] = useState(false)

  useEffect(() => setMounted(true), [])

  const form = useForm<AppearanceFormValues>({
    resolver: zodResolver(schema),
    defaultValues: {
      theme: 'dark',
    },
  })

  useEffect(() => {
    if (mounted && resolvedTheme) {
      form.setValue('theme', resolvedTheme as 'light' | 'dark')
    }
  }, [mounted, resolvedTheme, form])

  function onSubmit(data: AppearanceFormValues) {
    if (data.theme !== resolvedTheme) setTheme(data.theme)
    showSubmittedData(data)
  }

  if (!mounted) return null

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="theme"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Tema</FormLabel>
              <FormDescription>Seleccione el tema para el dashboard.</FormDescription>
              <FormMessage />
              <RadioGroup
                onValueChange={field.onChange}
                defaultValue={field.value}
                className="grid max-w-md grid-cols-2 gap-8 pt-2"
              >
                <FormItem>
                  <FormLabel className="[&:has([data-state=checked])>div]:border-ring flex flex-col items-center">
                    <FormControl>
                      <RadioGroupItem value="light" className="sr-only" />
                    </FormControl>
                    <div className="items-center rounded-md border-2 p-1 cursor-pointer">
                      <div className="space-y-2 rounded-sm bg-[#ecedef] p-2">
                        <div className="space-y-2 rounded-md bg-white p-2 shadow-xs">
                          <div className="h-2 w-[80px] rounded-lg bg-[#ecedef]" />
                          <div className="h-2 w-[100px] rounded-lg bg-[#ecedef]" />
                        </div>
                        <div className="flex items-center space-x-2 rounded-md bg-white p-2 shadow-xs">
                          <div className="h-4 w-4 rounded-full bg-[#ecedef]" />
                          <div className="h-2 w-[100px] rounded-lg bg-[#ecedef]" />
                        </div>
                        <div className="flex items-center space-x-2 rounded-md bg-white p-2 shadow-xs">
                          <div className="h-4 w-4 rounded-full bg-[#ecedef]" />
                          <div className="h-2 w-[100px] rounded-lg bg-[#ecedef]" />
                        </div>
                      </div>
                    </div>
                    <span className="mt-2 flex items-center justify-center gap-1 text-center">
                      <Sun className="size-4" />
                      Claro
                    </span>
                  </FormLabel>
                </FormItem>
                <FormItem>
                  <FormLabel className="[&:has([data-state=checked])>div]:border-ring flex flex-col items-center">
                    <FormControl>
                      <RadioGroupItem value="dark" className="sr-only" />
                    </FormControl>
                    <div className="items-center rounded-md border-2 p-1 cursor-pointer">
                      <div className="space-y-2 rounded-sm bg-[#18181b] p-2">
                        <div className="space-y-2 rounded-md bg-[#27272a] p-2 shadow-xs">
                          <div className="h-2 w-[80px] rounded-lg bg-[#ffffff1a]" />
                          <div className="h-2 w-[100px] rounded-lg bg-[#ffffff1a]" />
                        </div>
                        <div className="flex items-center space-x-2 rounded-md bg-[#27272a] p-2 shadow-xs">
                          <div className="h-4 w-4 rounded-full bg-[#ffffff1a]" />
                          <div className="h-2 w-[100px] rounded-lg bg-[#ffffff1a]" />
                        </div>
                        <div className="flex items-center space-x-2 rounded-md bg-[#27272a] p-2 shadow-xs">
                          <div className="h-4 w-4 rounded-full bg-[#ffffff1a]" />
                          <div className="h-2 w-[100px] rounded-lg bg-[#ffffff1a]" />
                        </div>
                      </div>
                    </div>
                    <span className="mt-2 flex items-center justify-center gap-1 text-center">
                      <Moon className="size-4" />
                      Oscuro
                    </span>
                  </FormLabel>
                </FormItem>
              </RadioGroup>
            </FormItem>
          )}
        />

        <div className="flex justify-center sm:justify-start">
          <Button type="submit">Actualizar preferencias</Button>
        </div>
      </form>
    </Form>
  )
}
