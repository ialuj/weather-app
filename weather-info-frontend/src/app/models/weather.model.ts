export interface WeatherResponse {
  city: string;
  current: CurrentWeather;
  aiSummary: AiSummary;
  forecast: ForecastDay[];
  timestamp: number;
}

export interface CurrentWeather {
  temperature: number;
  condition: string;
  humidity: number;
  windKph: number;
  feelsLike: number;
}

export interface AiSummary {
  text: string;
  recommendation: string;
}

export interface ForecastDay {
  date: string;
  maxTemp: number;
  minTemp: number;
  condition: string;
  icon?: string;
}
