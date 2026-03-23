'use client'

import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'
import { z } from 'zod'

import { Button } from '@/components/ui/button'
import { Checkbox } from '@/components/ui/checkbox'
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form'
import { showSubmittedData } from '@/lib/utils/components/show-submitted-data'

const items = [
  {
    id: 'recents',
    label: 'Recientes',
  },
  {
    id: 'home',
    label: 'Inicio',
  },
  {
    id: 'applications',
    label: 'Aplicaciones',
  },
  {
    id: 'desktop',
    label: 'Escritorio',
  },
  {
    id: 'downloads',
    label: 'Descargas',
  },
  {
    id: 'documents',
    label: 'Documentos',
  },
] as const

const displayFormSchema = z.object({
  items: z.array(z.string()).refine((value) => value.some((item) => item), {
    message: 'Debes seleccionar al menos un elemento.',
  }),
})

type DisplayFormValues = z.infer<typeof displayFormSchema>

const defaultValues: Partial<DisplayFormValues> = {
  items: ['recents', 'home'],
}

export function DisplayForm() {
  const form = useForm<DisplayFormValues>({
    resolver: zodResolver(displayFormSchema),
    defaultValues,
  })

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit((data) => showSubmittedData(data))} className="space-y-8">
        <FormField
          control={form.control}
          name="items"
          render={() => (
            <FormItem>
              <div className="mb-4">
                <FormLabel className="text-base">Barra lateral</FormLabel>
                <FormDescription>
                  Selecciona los elementos que deseas mostrar en la barra lateral.
                </FormDescription>
              </div>
              {items.map((item) => (
                <FormField
                  key={item.id}
                  control={form.control}
                  name="items"
                  render={({ field }) => {
                    return (
                      <FormItem key={item.id} className="flex flex-row items-start">
                        <FormControl>
                          <Checkbox
                            checked={field.value?.includes(item.id)}
                            onCheckedChange={(checked) => {
                              return checked
                                ? field.onChange([...field.value, item.id])
                                : field.onChange(field.value?.filter((value) => value !== item.id))
                            }}
                          />
                        </FormControl>
                        <FormLabel className="font-normal">{item.label}</FormLabel>
                      </FormItem>
                    )
                  }}
                />
              ))}
              <FormMessage />
            </FormItem>
          )}
        />
        <div className="flex justify-center sm:justify-start">
          <Button type="submit">Actualizar visualización</Button>
        </div>
      </form>
    </Form>
  )
}
