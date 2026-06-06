import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class GeolocationService {
  getCurrentPosition(): Observable<{ lat: number; lon: number }> {
    return new Observable((observer) => {
      if (!navigator.geolocation) {
        observer.error('Geolocation not supported');
        return;
      }
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          observer.next({
            lat: pos.coords.latitude,
            lon: pos.coords.longitude,
          });
          observer.complete();
        },
        (err) => observer.error('Location denied or unavailable'),
        { enableHighAccuracy: true, timeout: 10000 }
      );
    });
  }
}
