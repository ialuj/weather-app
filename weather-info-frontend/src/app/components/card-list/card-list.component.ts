import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgIf, NgFor, CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WeatherResponse } from '../../models/weather.model';
import { WeatherService } from '../../services/weather.service';

@Component({
  selector: 'app-card-list',
  standalone: true,
  imports: [NgIf, NgFor, CommonModule, FormsModule],
  templateUrl: './card-list.component.html',
  styleUrl: './card-list.component.scss',
})
export class CardListComponent implements OnInit {
  @Output() citySelected = new EventEmitter<WeatherResponse>();

  allCities: string[] = [];
  filteredCities: string[] = [];
  searchTerm: string = '';
  selectedCity: string = '';
  loading = false;

  // Filter values
  selectedDays: number = 3;
  includeAI: boolean = true;
  selectedUnits: string = 'metric';
  selectedLang: string = 'en';
  weatherType: string = 'weather';

  constructor(private weatherService: WeatherService) {}

  ngOnInit() {
    this.loadCities();
  }

  loadCities() {
    this.weatherService.getAllCities().subscribe({
      next: (cities) => {
        this.allCities = cities.sort();
        this.filteredCities = this.allCities;
      },
      error: (err) => console.error('Error loading cities:', err),
    });
  }

  filterCities() {
    this.filteredCities = this.allCities.filter((city) =>
      city.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  // Called when any filter changes - auto refresh current city
  onFiltersChange() {
    if (this.selectedCity) {
      this.selectCity(this.selectedCity);
    }
  }

  selectCity(city: string) {
    console.log('Selected city:', city);
    console.log('Filters:', {
      days: this.selectedDays,
      ai: this.includeAI,
      units: this.selectedUnits,
      lang: this.selectedLang,
      weatherType: this.weatherType,
    });

    this.selectedCity = city;
    this.loading = true;

    this.weatherService
      .getWeatherByCity(
        city,
        this.selectedDays,
        this.includeAI,
        this.selectedUnits,
        this.selectedLang,
        this.weatherType
      )
      .subscribe({
        next: (weather) => {
          this.citySelected.emit(weather);
          this.loading = false;
        },
        error: (err) => {
          console.error('Error fetching weather:', err);
          this.loading = false;
        },
      });
  }
}
