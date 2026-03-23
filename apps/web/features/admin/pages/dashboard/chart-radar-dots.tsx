'use client'

import { TrendingUp } from 'lucide-react'
import { PolarAngleAxis, PolarGrid, Radar, RadarChart } from 'recharts'

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
  { type: 'Estudiante', count: 600 },
  { type: 'Docente', count: 450 },
  { type: 'Administrativo', count: 350 },
  { type: 'Externo', count: 250 },
]

const chartConfig = {
  count: {
    label: 'Lectores',
    color: 'var(--chart-1)',
  },
} satisfies ChartConfig

export function ChartRadarDots() {
  return (
    <Card>
      <CardHeader className="items-center">
        <CardTitle>Composición de Lectores</CardTitle>
        <CardDescription>Distribución de usuarios por tipo.</CardDescription>
      </CardHeader>
      <CardContent className="pb-0">
        <ChartContainer config={chartConfig} className="mx-auto aspect-square max-h-[250px]">
          <RadarChart data={chartData}>
            <ChartTooltip cursor={false} content={<ChartTooltipContent />} />
            <PolarAngleAxis dataKey="type" />
            <PolarGrid />
            <Radar
              dataKey="count"
              fill="var(--color-count)"
              fillOpacity={0.6}
              dot={{
                r: 4,
                fillOpacity: 1,
              }}
            />
          </RadarChart>
        </ChartContainer>
      </CardContent>
      <CardFooter className="flex-col gap-2 text-sm">
        <div className="flex items-center gap-2 leading-none font-medium">
          El tipo Estudiante es el más numeroso. <TrendingUp className="h-4 w-4" />
        </div>
        <div className="text-muted-foreground flex items-center gap-2 leading-none">
          Datos de lectores activos.
        </div>
      </CardFooter>
    </Card>
  )
}
