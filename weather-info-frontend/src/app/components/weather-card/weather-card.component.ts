import { Component, Input, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WeatherResponse, ForecastDay } from '../../models/weather.model';
import { ForecastComponent } from '../forecast/forecast.component';

@Component({
  selector: 'app-weather-card',
  standalone: true,
  imports: [CommonModule, ForecastComponent],
  templateUrl: './weather-card.component.html',
  styleUrls: ['./weather-card.component.scss'],
})
export class WeatherCardComponent implements OnChanges {
  @Input() weather: WeatherResponse | null = null;
  @Input() units: string = 'metric'; // ← receber do parent

  weatherIcon: string = '';
  weatherClass: string = '';

  ngOnChanges() {
    if (this.weather) {
      this.updateWeatherTheme();
    }
  }

  private updateWeatherTheme() {
    const condition = this.weather?.current.condition?.toLowerCase() || '';
    const temp = this.weather?.current.temperature || 0;

    if (condition.includes('rain') || condition.includes('drizzle')) {
      this.weatherIcon = '🌧️';
      this.weatherClass = 'rainy';
    } else if (condition.includes('cloud')) {
      this.weatherIcon = '☁️';
      this.weatherClass = 'cloudy';
    } else if (condition.includes('sun') || temp > 25) {
      this.weatherIcon = '☀️';
      this.weatherClass = 'sunny';
    } else if (temp < 15) {
      this.weatherIcon = '❄️';
      this.weatherClass = 'cold';
    } else {
      this.weatherIcon = '🌤️';
      this.weatherClass = 'default';
    }
  }

  getWeatherIcon(condition: string): string {
    const cond = condition.toLowerCase();
    if (cond.includes('rain')) return '🌧️';
    if (cond.includes('cloud')) return '☁️';
    if (cond.includes('sun')) return '☀️';
    if (cond.includes('clear')) return '☀️';
    if (cond.includes('snow')) return '❄️';
    return '🌤️';
  }

  formatShortDate(dateStr: string): string {
    const date = new Date(dateStr);
    return date.toLocaleDateString('en-US', { weekday: 'short' });
  }

  formatTemp(temp: number): string {
    if (this.units === 'imperial') {
      return `${Math.round((temp * 9) / 5 + 32)}°`;
    }
    return `${Math.round(temp)}°`;
  }
}
