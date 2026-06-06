import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { WeatherResponse } from '../models/weather.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class WeatherService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getCities(): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/v1/cities/all`);
  }

  getWeatherByCity(
    city: string,
    days?: number,
    ai?: Boolean,
    units?: string,
    lang?: string,
    weatherType?: string
  ): Observable<WeatherResponse> {
    return this.http.get<WeatherResponse>(
      `${this.apiUrl}/v1/weather/by-city?city=${city}&days=${days}&ai=${ai}&units=${units}&lang=${lang}&weatherType=${weatherType}`
    );
  }

  getWeatherByCoords(
    lat: number,
    lon: number,
    days: number,
    ai: Boolean,
    weatherType: string,
    units?: string,
    lang?: string
  ): Observable<WeatherResponse> {
    return this.http.get<WeatherResponse>(
      `${this.apiUrl}/v1/weather/by-location?lat=${lat}&lon=${lon}&days=${days}&ai=${ai}&units=${units}&weatherType=${weatherType}&lang=${lang}`
    );
  }

  getAllCities(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/v1/cities/all`);
  }
}
