import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { WeatherCardComponent } from './components/weather-card/weather-card.component';
import { WeatherService } from './services/weather.service';
import { GeolocationService } from './services/geolocation.service';
import { WeatherResponse } from './models/weather.model';
import { MenuOption } from './components/sidebar/sidebar.component';
import { CardListComponent } from './components/card-list/card-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule, // ← necessário para ngModel nos filtros
    HttpClientModule,
    SidebarComponent,
    CardListComponent,
    WeatherCardComponent,
  ],
  providers: [WeatherService, GeolocationService],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  currentWeather: WeatherResponse | null = null;
  activeMenu: MenuOption = 'cities';
  loading = false;
  errorMessage: string | null = null;

  // Location filters
  locationDays: number = 3;
  locationAI: boolean = true;
  locationUnits: string = 'metric';
  locationLang: string = 'en';
  weatherType: string = 'weather';

  private lastCoords: { lat: number; lon: number } | null = null;

  constructor(
    private weatherService: WeatherService,
    private geolocation: GeolocationService
  ) {}

  onMenuChange(menu: MenuOption) {
    this.activeMenu = menu;
    this.errorMessage = null;

    if (menu === 'location') {
      this.getLocationWeather();
    } else if (menu === 'others') {
      this.currentWeather = null;
    }
  }

  onCitySelected(weather: WeatherResponse) {
    this.currentWeather = weather;
  }

  onLocationFiltersChange() {
    if (this.lastCoords && this.activeMenu === 'location') {
      this.fetchWeatherByCoords(this.lastCoords.lat, this.lastCoords.lon);
    }
    console.log('Filters:', {
      days: this.locationDays,
      ai: this.locationAI,
      units: this.locationUnits,
      lang: this.locationLang,
      weatherType: this.weatherType,
    });

    this.loading = true;

    this.getLocationWeather();
  }

  getLocationWeather() {
    this.loading = true;
    this.errorMessage = null;

    this.geolocation.getCurrentPosition().subscribe({
      next: (coords) => {
        this.weatherService
          .getWeatherByCoords(
            coords.lat,
            coords.lon,
            this.locationDays,
            this.locationAI,
            this.weatherType,
            this.locationUnits,
            this.locationLang
          )
          .subscribe({
            next: (weather) => {
              this.currentWeather = weather;
              this.loading = false;
            },
            error: () => {
              this.errorMessage = 'Could not fetch weather for your location';
              this.loading = false;
            },
          });
      },
      error: () => {
        this.errorMessage = 'Please enable location access to use this feature';
        this.loading = false;
      },
    });
  }

  private fetchWeatherByCoords(lat: number, lon: number) {
    this.loading = true;

    this.weatherService
      .getWeatherByCoords(
        lat,
        lon,
        this.locationDays,
        this.locationAI,
        this.locationUnits,
        this.locationLang
      )
      .subscribe({
        next: (weather) => {
          this.currentWeather = weather;
          this.loading = false;
        },
        error: () => {
          this.errorMessage = 'Could not fetch weather for your location';
          this.loading = false;
        },
      });
  }
}
