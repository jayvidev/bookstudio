import { z } from 'zod'

export const apiFieldErrorSchema = z.object({
  field: z.string(),
  message: z.string(),
  rejectedValue: z.unknown(),
})

export const apiErrorSchema = z.object({
  success: z.literal(false),
  status: z.number(),
  message: z.string(),
  path: z.string(),
  timestamp: z.string(),
  errors: z.array(apiFieldErrorSchema).nullable(),
})

export const apiSuccessSchema = <T extends z.ZodTypeAny>(dataSchema: T) =>
  z.object({
    success: z.literal(true),
    message: z.string(),
    data: dataSchema,
    timestamp: z.string(),
  })

export const apiResponseSchema = <T extends z.ZodTypeAny>(dataSchema: T) =>
  z.union([apiSuccessSchema(dataSchema), apiErrorSchema])

export type ApiFieldError = z.infer<typeof apiFieldErrorSchema>
export type ApiError = z.infer<typeof apiErrorSchema>
export type ApiSuccess<T> = {
  success: true
  message: string
  data: T
  timestamp: string
}
export type ApiResponse<T> = ApiSuccess<T> | ApiError

export function isApiSuccess<T>(response: ApiResponse<T>): response is ApiSuccess<T> {
  return response.success === true
}

export function isApiError<T>(response: ApiResponse<T>): response is ApiError {
  return response.success === false
}
