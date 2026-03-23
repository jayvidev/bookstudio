'use client'

import * as React from 'react'

import { Area, AreaChart, CartesianGrid, XAxis } from 'recharts'

import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import {
  ChartConfig,
  ChartContainer,
  ChartLegend,
  ChartLegendContent,
  ChartTooltip,
  ChartTooltipContent,
} from '@/components/ui/chart'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { ToggleGroup, ToggleGroupItem } from '@/components/ui/toggle-group'
import { useIsMobile } from '@/hooks/use-mobile'

const chartData = [
  { date: '2024-04-01', loans: 45, returns: 38 },
  { date: '2024-04-02', loans: 52, returns: 41 },
  { date: '2024-04-03', loans: 38, returns: 45 },
  { date: '2024-04-04', loans: 61, returns: 49 },
  { date: '2024-04-05', loans: 73, returns: 58 },
  { date: '2024-04-06', loans: 68, returns: 62 },
  { date: '2024-04-07', loans: 42, returns: 51 },
  { date: '2024-04-08', loans: 79, returns: 64 },
  { date: '2024-04-09', loans: 35, returns: 43 },
  { date: '2024-04-10', loans: 56, returns: 47 },
  { date: '2024-04-11', loans: 71, returns: 59 },
  { date: '2024-04-12', loans: 63, returns: 55 },
  { date: '2024-04-13', loans: 48, returns: 52 },
  { date: '2024-04-14', loans: 41, returns: 46 },
  { date: '2024-04-15', loans: 37, returns: 40 },
  { date: '2024-04-16', loans: 44, returns: 39 },
  { date: '2024-04-17', loans: 82, returns: 68 },
  { date: '2024-04-18', loans: 76, returns: 71 },
  { date: '2024-04-19', loans: 54, returns: 58 },
  { date: '2024-04-20', loans: 39, returns: 44 },
  { date: '2024-04-21', loans: 43, returns: 48 },
  { date: '2024-04-22', loans: 59, returns: 53 },
  { date: '2024-04-23', loans: 47, returns: 51 },
  { date: '2024-04-24', loans: 72, returns: 61 },
  { date: '2024-04-25', loans: 65, returns: 57 },
  { date: '2024-04-26', loans: 33, returns: 42 },
  { date: '2024-04-27', loans: 78, returns: 73 },
  { date: '2024-04-28', loans: 46, returns: 50 },
  { date: '2024-04-29', loans: 69, returns: 60 },
  { date: '2024-04-30', loans: 84, returns: 75 },
  { date: '2024-05-01', loans: 51, returns: 56 },
  { date: '2024-05-02', loans: 67, returns: 63 },
  { date: '2024-05-03', loans: 58, returns: 52 },
  { date: '2024-05-04', loans: 81, returns: 76 },
  { date: '2024-05-05', loans: 88, returns: 79 },
  { date: '2024-05-06', loans: 92, returns: 85 },
  { date: '2024-05-07', loans: 74, returns: 68 },
  { date: '2024-05-08', loans: 49, returns: 54 },
  { date: '2024-05-09', loans: 55, returns: 50 },
  { date: '2024-05-10', loans: 70, returns: 65 },
  { date: '2024-05-11', loans: 77, returns: 69 },
  { date: '2024-05-12', loans: 62, returns: 58 },
  { date: '2024-05-13', loans: 53, returns: 49 },
  { date: '2024-05-14', loans: 86, returns: 82 },
  { date: '2024-05-15', loans: 83, returns: 77 },
  { date: '2024-05-16', loans: 75, returns: 71 },
  { date: '2024-05-17', loans: 89, returns: 80 },
  { date: '2024-05-18', loans: 71, returns: 67 },
  { date: '2024-05-19', loans: 57, returns: 53 },
  { date: '2024-05-20', loans: 50, returns: 55 },
  { date: '2024-05-21', loans: 40, returns: 45 },
  { date: '2024-05-22', loans: 38, returns: 43 },
  { date: '2024-05-23', loans: 64, returns: 60 },
  { date: '2024-05-24', loans: 68, returns: 62 },
  { date: '2024-05-25', loans: 60, returns: 56 },
  { date: '2024-05-26', loans: 54, returns: 50 },
  { date: '2024-05-27', loans: 85, returns: 79 },
  { date: '2024-05-28', loans: 61, returns: 57 },
  { date: '2024-05-29', loans: 36, returns: 41 },
  { date: '2024-05-30', loans: 73, returns: 66 },
  { date: '2024-05-31', loans: 52, returns: 58 },
  { date: '2024-06-01', loans: 55, returns: 51 },
  { date: '2024-06-02', loans: 87, returns: 81 },
  { date: '2024-06-03', loans: 44, returns: 48 },
  { date: '2024-06-04', loans: 80, returns: 74 },
  { date: '2024-06-05', loans: 41, returns: 46 },
  { date: '2024-06-06', loans: 66, returns: 61 },
  { date: '2024-06-07', loans: 72, returns: 68 },
  { date: '2024-06-08', loans: 78, returns: 72 },
  { date: '2024-06-09', loans: 91, returns: 84 },
  { date: '2024-06-10', loans: 53, returns: 59 },
  { date: '2024-06-11', loans: 42, returns: 47 },
  { date: '2024-06-12', loans: 88, returns: 82 },
  { date: '2024-06-13', loans: 39, returns: 44 },
  { date: '2024-06-14', loans: 82, returns: 76 },
  { date: '2024-06-15', loans: 70, returns: 65 },
  { date: '2024-06-16', loans: 76, returns: 70 },
  { date: '2024-06-17', loans: 93, returns: 87 },
  { date: '2024-06-18', loans: 48, returns: 53 },
  { date: '2024-06-19', loans: 74, returns: 68 },
  { date: '2024-06-20', loans: 84, returns: 78 },
  { date: '2024-06-21', loans: 56, returns: 61 },
  { date: '2024-06-22', loans: 69, returns: 64 },
  { date: '2024-06-23', loans: 94, returns: 88 },
  { date: '2024-06-24', loans: 51, returns: 56 },
  { date: '2024-06-25', loans: 49, returns: 54 },
  { date: '2024-06-26', loans: 81, returns: 75 },
  { date: '2024-06-27', loans: 86, returns: 80 },
  { date: '2024-06-28', loans: 57, returns: 62 },
  { date: '2024-06-29', loans: 45, returns: 50 },
  { date: '2024-06-30', loans: 85, returns: 79 },
]

const chartConfig = {
  visitors: {
    label: 'Actividad',
  },
  loans: {
    label: 'Préstamos',
    color: 'var(--chart-2)',
  },
  returns: {
    label: 'Devoluciones',
    color: 'var(--chart-1)',
  },
} satisfies ChartConfig

export function ChartAreaInteractive() {
  const isMobile = useIsMobile()
  const [timeRange, setTimeRange] = React.useState('90d')

  React.useEffect(() => {
    if (isMobile) {
      setTimeRange('7d')
    }
  }, [isMobile])

  const filteredData = chartData.filter((item) => {
    const date = new Date(item.date)
    const referenceDate = new Date('2024-06-30')
    let daysToSubtract = 90
    if (timeRange === '30d') {
      daysToSubtract = 30
    } else if (timeRange === '7d') {
      daysToSubtract = 7
    }
    const startDate = new Date(referenceDate)
    startDate.setDate(startDate.getDate() - daysToSubtract)
    return date >= startDate
  })

  return (
    <Card className="@container/card">
      <CardHeader>
        <CardTitle>Actividad Total</CardTitle>
        <CardDescription>
          <span className="hidden @[540px]/card:block">
            Préstamos y devoluciones de los últimos 3 meses
          </span>
          <span className="@[540px]/card:hidden">Últimos 3 meses</span>
        </CardDescription>
        <CardAction>
          <ToggleGroup
            type="single"
            value={timeRange}
            onValueChange={setTimeRange}
            variant="outline"
            className="hidden *:data-[slot=toggle-group-item]:!px-4 @[767px]/card:flex"
          >
            <ToggleGroupItem value="90d">Últimos 3 meses</ToggleGroupItem>
            <ToggleGroupItem value="30d">Últimos 30 días</ToggleGroupItem>
            <ToggleGroupItem value="7d">Últimos 7 días</ToggleGroupItem>
          </ToggleGroup>
          <Select value={timeRange} onValueChange={setTimeRange}>
            <SelectTrigger
              className="flex w-40 **:data-[slot=select-value]:block **:data-[slot=select-value]:truncate @[767px]/card:hidden"
              size="sm"
              aria-label="Select a value"
            >
              <SelectValue placeholder="Últimos 3 meses" />
            </SelectTrigger>
            <SelectContent className="rounded-xl">
              <SelectItem value="90d" className="rounded-lg">
                Últimos 3 meses
              </SelectItem>
              <SelectItem value="30d" className="rounded-lg">
                Últimos 30 días
              </SelectItem>
              <SelectItem value="7d" className="rounded-lg">
                Últimos 7 días
              </SelectItem>
            </SelectContent>
          </Select>
        </CardAction>
      </CardHeader>
      <CardContent className="px-2 pt-4 sm:px-6 sm:pt-6">
        <ChartContainer config={chartConfig} className="aspect-auto h-[250px] w-full">
          <AreaChart data={filteredData}>
            <defs>
              <linearGradient id="fillLoans" x1="0" y1="0" x2="0" y2="1">
                <stop offset="5%" stopColor="var(--color-loans)" stopOpacity={1.0} />
                <stop offset="95%" stopColor="var(--color-loans)" stopOpacity={0.1} />
              </linearGradient>
              <linearGradient id="fillReturns" x1="0" y1="0" x2="0" y2="1">
                <stop offset="5%" stopColor="var(--color-returns)" stopOpacity={0.8} />
                <stop offset="95%" stopColor="var(--color-returns)" stopOpacity={0.1} />
              </linearGradient>
            </defs>
            <CartesianGrid vertical={false} />
            <XAxis
              dataKey="date"
              tickLine={false}
              axisLine={false}
              tickMargin={8}
              minTickGap={32}
              tickFormatter={(value) => {
                const date = new Date(value)
                return date.toLocaleDateString('es-ES', {
                  month: 'short',
                  day: 'numeric',
                })
              }}
            />
            <ChartTooltip
              cursor={false}
              content={
                <ChartTooltipContent
                  className="min-w-40"
                  labelFormatter={(value) => {
                    return new Date(value).toLocaleDateString('es-ES', {
                      month: 'short',
                      day: 'numeric',
                    })
                  }}
                  indicator="dot"
                />
              }
            />
            <Area
              dataKey="loans"
              type="natural"
              fill="url(#fillLoans)"
              stroke="var(--color-loans)"
              stackId="a"
            />
            <Area
              dataKey="returns"
              type="natural"
              fill="url(#fillReturns)"
              stroke="var(--color-returns)"
              stackId="a"
            />
            <ChartLegend content={<ChartLegendContent />} />
          </AreaChart>
        </ChartContainer>
      </CardContent>
    </Card>
  )
}
