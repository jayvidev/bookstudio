'use client'

import { TrendingUp } from 'lucide-react'
import { Bar, BarChart, XAxis, YAxis } from 'recharts'

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import {
  ChartConfig,
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from '@/components/ui/chart'

const chartData = [
  { level: 'primary', books: 320, fill: 'var(--color-primary)' },
  { level: 'secondary', books: 450, fill: 'var(--color-secondary)' },
  { level: 'superior', books: 610, fill: 'var(--color-superior)' },
  { level: 'general', books: 180, fill: 'var(--color-general)' },
]

const chartConfig = {
  books: {
    label: 'Libros',
  },
  primary: {
    label: 'Primaria',
    color: 'var(--chart-1)',
  },
  secondary: {
    label: 'Secundaria',
    color: 'var(--chart-2)',
  },
  superior: {
    label: 'Superior',
    color: 'var(--chart-3)',
  },
  general: {
    label: 'General',
    color: 'var(--chart-5)',
  },
} satisfies ChartConfig

export function ChartBarMixed() {
  return (
    <Card>
      <CardHeader>
        <CardTitle>Libros por Nivel de Categoría</CardTitle>
        <CardDescription>Distribución del inventario por nivel educativo.</CardDescription>
      </CardHeader>
      <CardContent>
        <ChartContainer config={chartConfig}>
          <BarChart
            accessibilityLayer
            data={chartData}
            layout="vertical"
            margin={{
              left: 20,
            }}
          >
            <YAxis
              dataKey="level"
              type="category"
              tickLine={false}
              tickMargin={10}
              axisLine={false}
              tickFormatter={(value) => chartConfig[value as keyof typeof chartConfig]?.label}
            />
            <XAxis dataKey="books" type="number" hide />
            <ChartTooltip cursor={false} content={<ChartTooltipContent hideLabel />} />
            <Bar dataKey="books" layout="vertical" radius={5} />
          </BarChart>
        </ChartContainer>
      </CardContent>
      <CardFooter className="flex-col items-start gap-2 text-sm">
        <div className="flex gap-2 leading-none font-medium">
          La categoría Superior tiene el mayor número de títulos.
          <TrendingUp className="h-4 w-4" />
        </div>
        <div className="text-muted-foreground leading-none">Total de categorías analizadas.</div>
      </CardFooter>
    </Card>
  )
}
