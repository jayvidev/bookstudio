'use client'

import { useState } from 'react'

import { addMonths, format, setMonth, setYear } from 'date-fns'
import { es } from 'date-fns/locale'
import { CalendarIcon } from 'lucide-react'
import type { DateRange } from 'react-day-picker'

import { Button } from '@/components/ui/button'
import { Calendar } from '@/components/ui/calendar'
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { cn } from '@/lib/utils'

type DateRangePickerProps = {
  value?: DateRange
  onDateSelect?: (range: DateRange | undefined) => void
  placeholder?: string
}

export const DateRangePicker = ({ value, onDateSelect, placeholder }: DateRangePickerProps) => {
  const [open, setOpen] = useState(false)
  const [leftMonth, setLeftMonth] = useState(new Date())
  const [rightMonth, setRightMonth] = useState(addMonths(new Date(), 1))

  const months = [
    'Enero',
    'Febrero',
    'Marzo',
    'Abril',
    'Mayo',
    'Junio',
    'Julio',
    'Agosto',
    'Septiembre',
    'Octubre',
    'Noviembre',
    'Diciembre',
  ]

  const currentYear = new Date().getFullYear()
  const years = Array.from({ length: 10 }, (_, i) => currentYear - 5 + i)

  const capitalizeFirst = (str: string) => str.charAt(0).toUpperCase() + str.slice(1)

  const handleSelect = (range: DateRange | undefined) => {
    onDateSelect?.(range)
  }

  return (
    <div className="grid gap-2">
      <Popover open={open} onOpenChange={setOpen}>
        <PopoverTrigger asChild>
          <Button
            size="sm"
            id="date"
            variant="outline"
            className={cn(
              'w-[240px] justify-start text-left font-normal',
              !value && 'text-muted-foreground'
            )}
          >
            <CalendarIcon />
            {value?.from ? (
              value.to ? (
                <>
                  {capitalizeFirst(format(value.from, 'dd MMM yyyy', { locale: es }))} -{' '}
                  {capitalizeFirst(format(value.to, 'dd MMM yyyy', { locale: es }))}
                </>
              ) : (
                capitalizeFirst(format(value.from, 'dd MMM yyyy', { locale: es }))
              )
            ) : (
              <span>{placeholder ?? 'Selecciona un rango de fechas'}</span>
            )}
          </Button>
        </PopoverTrigger>

        <PopoverContent className="w-auto p-0" align="start">
          <div className="flex flex-col">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-2 p-2">
              {/* Calendario izquierdo */}
              <div className="flex flex-col">
                <div className="flex justify-between p-2 gap-2">
                  <Select
                    value={leftMonth.getMonth().toString()}
                    onValueChange={(val) => setLeftMonth(setMonth(leftMonth, parseInt(val)))}
                  >
                    <SelectTrigger className="flex-1">
                      <SelectValue placeholder="Mes" />
                    </SelectTrigger>
                    <SelectContent>
                      {months.map((month, index) => (
                        <SelectItem key={index} value={index.toString()}>
                          {month}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                  <Select
                    value={leftMonth.getFullYear().toString()}
                    onValueChange={(val) => setLeftMonth(setYear(leftMonth, parseInt(val)))}
                  >
                    <SelectTrigger className="w-24">
                      <SelectValue placeholder="Año" />
                    </SelectTrigger>
                    <SelectContent>
                      {years.map((year) => (
                        <SelectItem key={year} value={year.toString()}>
                          {year}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>

                <Calendar
                  mode="range"
                  selected={value}
                  onSelect={handleSelect}
                  month={leftMonth}
                  onMonthChange={setLeftMonth}
                  locale={es}
                  showOutsideDays
                  numberOfMonths={1}
                />
              </div>

              {/* Calendario derecho */}
              <div className="flex flex-col">
                <div className="flex justify-between p-2 gap-2">
                  <Select
                    value={rightMonth.getMonth().toString()}
                    onValueChange={(val) => setRightMonth(setMonth(rightMonth, parseInt(val)))}
                  >
                    <SelectTrigger className="flex-1">
                      <SelectValue placeholder="Mes" />
                    </SelectTrigger>
                    <SelectContent>
                      {months.map((month, index) => (
                        <SelectItem key={index} value={index.toString()}>
                          {month}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                  <Select
                    value={rightMonth.getFullYear().toString()}
                    onValueChange={(val) => setRightMonth(setYear(rightMonth, parseInt(val)))}
                  >
                    <SelectTrigger className="w-24">
                      <SelectValue placeholder="Año" />
                    </SelectTrigger>
                    <SelectContent>
                      {years.map((year) => (
                        <SelectItem key={year} value={year.toString()}>
                          {year}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>

                <Calendar
                  mode="range"
                  selected={value}
                  onSelect={handleSelect}
                  month={rightMonth}
                  onMonthChange={setRightMonth}
                  locale={es}
                  showOutsideDays
                  numberOfMonths={1}
                />
              </div>
            </div>
          </div>
        </PopoverContent>
      </Popover>
    </div>
  )
}
