import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ForecastDay } from '../../models/weather.model';

@Component({
  selector: 'app-forecast',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './forecast.component.html',
  styleUrls: ['./forecast.component.scss'],
})
export class ForecastComponent {
  @Input() forecast: ForecastDay[] = [];
  @Input() units: string = 'metric';

  getWeatherIcon(condition: string): string {
    const cond = condition.toLowerCase();
    if (cond.includes('rain') || cond.includes('drizzle')) return '🌧️';
    if (cond.includes('cloud')) return '☁️';
    if (cond.includes('sun')) return '☀️';
    if (cond.includes('clear')) return '☀️';
    if (cond.includes('snow')) return '❄️';
    if (cond.includes('thunder')) return '⛈️';
    if (cond.includes('fog')) return '🌫️';
    return '🌤️';
  }

  formatWeekday(dateStr: string): string {
    const date = new Date(dateStr);
    return date.toLocaleDateString('en-US', { weekday: 'short' });
  }

  formatFullDate(dateStr: string): string {
    const date = new Date(dateStr);
    return date.toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
      year: 'numeric',
    });
  }

  formatTemp(temp: number): string {
    if (this.units === 'imperial') {
      return `${Math.round((temp * 9) / 5 + 32)}°F`;
    }
    return `${Math.round(temp)}°C`;
  }
}
