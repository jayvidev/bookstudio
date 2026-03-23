type PageMeta = {
  title: string
  authOnly?: boolean
  showInSidebar?: boolean
  resource?: string
}

export const pageMap: Record<string, PageMeta> = {
  '/admin': { title: 'Dashboard', authOnly: true, showInSidebar: true },

  '/admin/prestamos': {
    title: 'Préstamos',
    authOnly: true,
    showInSidebar: true,
    resource: 'loans',
  },
  '/admin/reservas': {
    title: 'Reservas',
    authOnly: true,
    showInSidebar: true,
    resource: 'reservations',
  },
  '/admin/multas': { title: 'Multas', authOnly: true, showInSidebar: true, resource: 'fines' },
  '/admin/pagos': { title: 'Pagos', authOnly: true, showInSidebar: true, resource: 'payments' },

  '/admin/libros': { title: 'Libros', authOnly: true, showInSidebar: true, resource: 'books' },
  '/admin/ejemplares': {
    title: 'Ejemplares',
    authOnly: true,
    showInSidebar: true,
    resource: 'copies',
  },
  '/admin/autores': { title: 'Autores', authOnly: true, showInSidebar: true, resource: 'authors' },
  '/admin/editoriales': {
    title: 'Editoriales',
    authOnly: true,
    showInSidebar: true,
    resource: 'publishers',
  },
  '/admin/categorias': {
    title: 'Categorías',
    authOnly: true,
    showInSidebar: true,
    resource: 'categories',
  },
  '/admin/ubicaciones': {
    title: 'Ubicaciones',
    authOnly: true,
    showInSidebar: true,
    resource: 'locations',
  },

  '/admin/lectores': {
    title: 'Lectores',
    authOnly: true,
    showInSidebar: true,
    resource: 'readers',
  },
  '/admin/trabajadores': {
    title: 'Trabajadores',
    authOnly: true,
    showInSidebar: true,
    resource: 'workers',
  },
  '/admin/roles': { title: 'Roles', authOnly: true, showInSidebar: true, resource: 'roles' },

  '/admin/ajustes': { title: 'Ajustes', authOnly: true, showInSidebar: true },
  '/admin/ajustes/cuenta': { title: 'Cuenta', authOnly: true },
  '/admin/ajustes/apariencia': { title: 'Apariencia', authOnly: true },
  '/admin/ajustes/notificaciones': { title: 'Notificaciones', authOnly: true },
  '/admin/ajustes/visualizacion': { title: 'Visualización', authOnly: true },

  '/admin/centro-de-ayuda': { title: 'Centro de ayuda', authOnly: true, showInSidebar: true },

  '/iniciar-sesion': { title: 'Iniciar sesión' },
  '/recuperar-contrasena': { title: 'Recuperar contraseña' },
  '/restablecer-contrasena': { title: 'Restablecer contraseña' },

  '/error/401': { title: 'Acceso no autorizado' },
  '/error/403': { title: 'Acceso denegado' },
  '/error/404': { title: 'Página no encontrada' },
  '/error/500': { title: 'Algo salió mal' },
  '/error/503': { title: 'Sitio en mantenimiento' },
} as const

export type ValidUrl = keyof typeof pageMap
