import { ChartAreaInteractive } from './chart-area-interactive'
import { ChartBarMixed } from './chart-bar-mixed'
import { ChartPieLegend } from './chart-pie-legend'
import { ChartRadarDots } from './chart-radar-dots'
import { SectionCards } from './section-cards'

export function DashboardPage() {
  return (
    <>
      <div className="@container/main flex flex-1 flex-col gap-2 mt-2">
        <div className="flex flex-col gap-4">
          <SectionCards />
          <ChartAreaInteractive />
          <div className="grid grid-cols-1 xl:grid-cols-3 gap-4">
            <ChartPieLegend />
            <ChartRadarDots />
            <ChartBarMixed />
          </div>
        </div>
      </div>
    </>
  )
}
