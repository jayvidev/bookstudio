import * as React from 'react'

import { Slot } from '@radix-ui/react-slot'
import { cva, type VariantProps } from 'class-variance-authority'

import { cn } from '@/lib/utils'

const badgeVariants = cva(
  'inline-flex items-center justify-center rounded-md border px-2 py-0.5 text-xs font-medium w-fit whitespace-nowrap shrink-0 [&>svg]:size-3 gap-1 [&>svg]:pointer-events-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive transition-[color,box-shadow] overflow-hidden',
  {
    variants: {
      variant: {
        default: 'border-transparent bg-primary text-primary-foreground [a&]:hover:bg-primary/90',
        secondary:
          'border-transparent bg-secondary text-secondary-foreground [a&]:hover:bg-secondary/90',
        destructive:
          'border-transparent bg-destructive text-white [a&]:hover:bg-destructive/90 focus-visible:ring-destructive/20 dark:focus-visible:ring-destructive/40 dark:bg-destructive/60',
        outline: 'text-foreground [a&]:hover:bg-accent [a&]:hover:text-accent-foreground',
        success:
          'bg-green-100 text-green-600 border border-green-200 dark:bg-green-600/20 dark:text-green-300 dark:border-green-500/40',
        warning:
          'bg-yellow-100 text-yellow-600 border border-yellow-200 dark:bg-yellow-600/20 dark:text-yellow-300 dark:border-yellow-500/40',
        info: 'bg-blue-100 text-blue-600 border border-blue-200 dark:bg-blue-600/20 dark:text-blue-300 dark:border-blue-500/40',
        muted:
          'bg-zinc-100 text-zinc-600 border border-zinc-200 dark:bg-zinc-600/20 dark:text-zinc-300 dark:border-zinc-500/40',
        danger:
          'bg-red-100 text-red-600 border border-red-200 dark:bg-red-600/20 dark:text-red-300 dark:border-red-500/40',
        brand:
          'bg-violet-100 text-violet-600 border border-violet-200 dark:bg-violet-600/20 dark:text-violet-300 dark:border-violet-500/40',
        warm: 'bg-orange-100 text-orange-700 border border-orange-200 dark:bg-orange-700/20 dark:text-orange-300 dark:border-orange-500/40',
        fresh:
          'bg-cyan-100 text-cyan-700 border border-cyan-200 dark:bg-cyan-700/20 dark:text-cyan-300 dark:border-cyan-500/40',
        flow: 'bg-teal-100 text-teal-700 border border-teal-200 dark:bg-teal-700/20 dark:text-teal-300 dark:border-teal-500/40',
        active:
          'bg-pink-100 text-pink-700 border border-pink-200 dark:bg-pink-700/20 dark:text-pink-300 dark:border-pink-500/40',
        bright:
          'bg-lime-100 text-lime-700 border border-lime-200 dark:bg-lime-700/20 dark:text-lime-300 dark:border-lime-500/40',
      },
    },
    defaultVariants: {
      variant: 'default',
    },
  }
)

function Badge({
  className,
  variant,
  asChild = false,
  ...props
}: React.ComponentProps<'span'> & VariantProps<typeof badgeVariants> & { asChild?: boolean }) {
  const Comp = asChild ? Slot : 'span'

  return <Comp data-slot="badge" className={cn(badgeVariants({ variant }), className)} {...props} />
}

export { Badge, badgeVariants }
