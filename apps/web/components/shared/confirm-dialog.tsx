'use client'

import { useRef } from 'react'

import Link from 'next/link'

import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from '@/components/ui/alert-dialog'
import { buttonVariants } from '@/components/ui/button'

interface ConfirmActionButton {
  label: string
  variant?: 'default' | 'destructive' | 'outline' | 'secondary' | 'ghost' | 'link'
  icon?: React.ReactNode
}

interface ConfirmDialogProps {
  title: string
  description: string
  actionButton: ConfirmActionButton
  cancelLabel?: string
  onConfirm?: () => void
  onOpenChange?: (open: boolean) => void
  to?: string
  open?: boolean
  children?: React.ReactNode
}

export function ConfirmDialog({
  title,
  description,
  actionButton,
  cancelLabel = 'Cancelar',
  onConfirm,
  onOpenChange,
  to,
  open,
  children,
}: ConfirmDialogProps) {
  const actionButtonRef = useRef<HTMLButtonElement>(null)

  return (
    <AlertDialog open={open} onOpenChange={onOpenChange}>
      {children && <AlertDialogTrigger asChild>{children}</AlertDialogTrigger>}
      <AlertDialogContent
        onOpenAutoFocus={(e) => {
          e.preventDefault()
          actionButtonRef.current?.focus()
        }}
      >
        <AlertDialogHeader>
          <AlertDialogTitle>{title}</AlertDialogTitle>
          <AlertDialogDescription>{description}</AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>{cancelLabel}</AlertDialogCancel>
          {to ? (
            <AlertDialogAction
              asChild
              className={buttonVariants({
                variant: actionButton.variant ?? 'default',
              })}
              ref={actionButtonRef}
            >
              <Link href={to}>
                {actionButton.icon} {actionButton.label}
              </Link>
            </AlertDialogAction>
          ) : (
            <AlertDialogAction
              className={buttonVariants({
                variant: actionButton.variant ?? 'default',
              })}
              onClick={onConfirm}
              ref={actionButtonRef}
            >
              {actionButton.icon} {actionButton.label}
            </AlertDialogAction>
          )}
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  )
}
