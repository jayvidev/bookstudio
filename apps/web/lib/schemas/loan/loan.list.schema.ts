import { z } from 'zod'

export const loanListSchema = z.object({
  id: z.number(),
  code: z.string(),
  reader: z.object({
    id: z.number(),
    code: z.string(),
    fullName: z.string(),
  }),
  loanDate: z.coerce.date(),
  itemCount: z.number(),
  statusCounts: z.object({
    borrowed: z.number(),
    returned: z.number(),
    overdue: z.number(),
    lost: z.number(),
    canceled: z.number(),
  }),
})

export type LoanList = z.infer<typeof loanListSchema>
