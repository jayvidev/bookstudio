import { config } from '@/config'
import { ApiError, ApiResponse } from '@/lib/schemas/common/api-response.schema'

export class ApiClientError extends Error {
  constructor(
    public status: number,
    public path: string,
    message: string,
    public errors?: ApiError['errors']
  ) {
    super(message)
  }

  static fromApiError(error: ApiError) {
    return new ApiClientError(error.status, error.path, error.message, error.errors)
  }
}

export class ApiClient {
  constructor(private baseUrl: string = config.api.baseUrl) {}

  private async request<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
    const url = `${this.baseUrl}${endpoint}`

    const res = await fetch(url, {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    })

    const json = (await res.json()) as ApiResponse<T>

    if (!json.success) {
      throw ApiClientError.fromApiError(json)
    }

    return json.data
  }

  get<T>(endpoint: string, options?: RequestInit) {
    return this.request<T>(endpoint, { method: 'GET', ...options })
  }

  post<T>(endpoint: string, body?: unknown, options?: RequestInit) {
    return this.request<T>(endpoint, {
      method: 'POST',
      body: body ? JSON.stringify(body) : undefined,
      ...options,
    })
  }

  put<T>(endpoint: string, body?: unknown, options?: RequestInit) {
    return this.request<T>(endpoint, {
      method: 'PUT',
      body: body ? JSON.stringify(body) : undefined,
      ...options,
    })
  }

  delete<T>(endpoint: string, options?: RequestInit) {
    return this.request<T>(endpoint, { method: 'DELETE', ...options })
  }
}

export const apiClient = new ApiClient()
