import { z } from 'zod'

export const roleListSchema = z.object({
  id: z.number(),
  name: z.string(),
  description: z.string(),
  permissionCount: z.number(),
})

export type RoleList = z.infer<typeof roleListSchema>
