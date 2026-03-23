'use client'

import { Pie, PieChart } from 'recharts'

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { ChartConfig, ChartContainer, ChartLegend, ChartLegendContent } from '@/components/ui/chart'

const chartData = [
  { status: 'available', copies: 650, fill: 'var(--color-available)' },
  { status: 'loaned', copies: 250, fill: 'var(--color-loaned)' },
  { status: 'reserved', copies: 80, fill: 'var(--color-reserved)' },
  { status: 'maintenance', copies: 20, fill: 'var(--color-maintenance)' },
  { status: 'lost', copies: 5, fill: 'var(--color-lost)' },
]

const chartConfig = {
  copies: {
    label: 'Copias',
  },
  available: {
    label: 'Disponible',
    color: 'var(--chart-1)',
  },
  loaned: {
    label: 'Prestado',
    color: 'var(--chart-2)',
  },
  reserved: {
    label: 'Reservado',
    color: 'var(--chart-3)',
  },
  maintenance: {
    label: 'Mantenimiento',
    color: 'var(--chart-4)',
  },
  lost: {
    label: 'Extraviado',
    color: 'var(--chart-5)',
  },
} satisfies ChartConfig

export function ChartPieLegend() {
  return (
    <Card className="flex flex-col">
      <CardHeader className="items-center pb-0">
        <CardTitle>Distribución de Copias por Estado</CardTitle>
        <CardDescription>Estado actual de las copias de libros.</CardDescription>
      </CardHeader>
      <CardContent className="flex-1 pb-0">
        <ChartContainer config={chartConfig} className="mx-auto aspect-square max-h-[300px]">
          <PieChart>
            <Pie data={chartData} dataKey="copies" />
            <ChartLegend
              content={<ChartLegendContent nameKey="status" />}
              className="-translate-y-2 flex-wrap gap-2 *:basis-1/4 *:justify-center"
            />
          </PieChart>
        </ChartContainer>
      </CardContent>
    </Card>
  )
}
