import { z } from 'zod'

export const OptionSchema = z.object({
  value: z.string().or(z.number()).transform(String),
  label: z.string(),
})

export type Option = z.infer<typeof OptionSchema>

export const optionsArraySchema = z.array(OptionSchema)
