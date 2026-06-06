package com.weather_io.weather_info_api.service;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class CityService {

    private final Map<String, double[]> cityCoordinates = Map.ofEntries(
            // Southern Africa
            Map.entry("Maputo", new double[]{-25.9692, 32.5732}),
            Map.entry("Pretoria", new double[]{-25.7566, 28.1900}),
            Map.entry("Cape Town", new double[]{-33.9221, 18.4233}),
            Map.entry("Bloemfontein", new double[]{-29.1129, 26.1900}),
            Map.entry("Johannesburg", new double[]{-26.2044, 28.0456}),
            Map.entry("Durban", new double[]{-29.8587, 31.0218}),
            Map.entry("Windhoek", new double[]{-22.5649, 17.0833}),
            Map.entry("Gaborone", new double[]{-24.6580, 25.9083}),
            Map.entry("Maseru", new double[]{-29.3151, 27.4833}),
            Map.entry("Mbabane", new double[]{-26.3264, 31.1333}),
            Map.entry("Lusaka", new double[]{-15.4155, 28.2833}),
            Map.entry("Harare", new double[]{-17.8263, 31.0500}),
            Map.entry("Lilongwe", new double[]{-13.9865, 33.7833}),
            Map.entry("Antananarivo", new double[]{-18.9185, 47.5333}),

            // East Africa
            Map.entry("Nairobi", new double[]{-1.2921, 36.8219}),
            Map.entry("Mombasa", new double[]{-4.0500, 39.6667}),
            Map.entry("Kampala", new double[]{0.3152, 32.5833}),
            Map.entry("Dar es Salaam", new double[]{-6.8161, 39.2803}),
            Map.entry("Dodoma", new double[]{-6.1811, 35.7500}),
            Map.entry("Kigali", new double[]{-1.9441, 30.0589}),
            Map.entry("Bujumbura", new double[]{-3.3833, 29.3667}),
            Map.entry("Gitega", new double[]{-3.4273, 29.9300}),
            Map.entry("Addis Ababa", new double[]{9.0192, 38.7483}),
            Map.entry("Asmara", new double[]{15.3317, 38.9333}),
            Map.entry("Djibouti", new double[]{11.5886, 42.9500}),
            Map.entry("Mogadishu", new double[]{2.0372, 45.3500}),
            Map.entry("Juba", new double[]{4.8539, 31.5667}),
            Map.entry("Moroni", new double[]{-11.7061, 43.2556}),
            Map.entry("Victoria", new double[]{-4.6232, 55.4500}),
            Map.entry("Port Louis", new double[]{-20.1609, 57.5000}),

            // North Africa
            Map.entry("Cairo", new double[]{30.0444, 31.2358}),
            Map.entry("Alexandria", new double[]{31.2000, 29.9000}),
            Map.entry("Khartoum", new double[]{15.5974, 32.5500}),
            Map.entry("Omdurman", new double[]{15.6333, 32.4833}),
            Map.entry("Tripoli", new double[]{32.8877, 13.1833}),
            Map.entry("Tunis", new double[]{36.8065, 10.1667}),
            Map.entry("Algiers", new double[]{36.7538, 3.0589}),
            Map.entry("Rabat", new double[]{34.0084, -6.8333}),
            Map.entry("Casablanca", new double[]{33.5333, -7.5833}),
            Map.entry("Nouakchott", new double[]{18.0735, -15.9500}),
            Map.entry("Bamako", new double[]{12.6392, -8.0000}),
            Map.entry("Ouagadougou", new double[]{12.3714, -1.5192}),
            Map.entry("Niamey", new double[]{13.5116, 2.1167}),

            // West Africa
            Map.entry("Dakar", new double[]{14.7167, -17.4667}),
            Map.entry("Banjul", new double[]{13.4544, -16.6500}),
            Map.entry("Bissau", new double[]{11.8632, -15.6000}),
            Map.entry("Conakry", new double[]{9.5091, -13.6833}),
            Map.entry("Freetown", new double[]{8.4871, -13.2333}),
            Map.entry("Monrovia", new double[]{6.3156, -10.8000}),
            Map.entry("Abidjan", new double[]{5.3167, -4.0333}),
            Map.entry("Yamoussoukro", new double[]{6.8163, -5.2833}),
            Map.entry("Accra", new double[]{5.5593, -0.2167}),
            Map.entry("Kumasi", new double[]{6.7000, -1.6250}),
            Map.entry("Lome", new double[]{6.1296, 1.2167}),
            Map.entry("Cotonou", new double[]{6.3562, 2.4389}),
            Map.entry("Porto-Novo", new double[]{6.4786, 2.6167}),
            Map.entry("Abuja", new double[]{9.0563, 7.1833}),
            Map.entry("Lagos", new double[]{6.4550, 3.3841}),
            Map.entry("Ibadan", new double[]{7.3964, 3.9167}),
            Map.entry("Kano", new double[]{12.0000, 8.5167}),

            // Central Africa
            Map.entry("Yaounde", new double[]{3.8617, 11.5167}),
            Map.entry("Douala", new double[]{4.0500, 9.7000}),
            Map.entry("Libreville", new double[]{0.4078, 9.4333}),
            Map.entry("Malabo", new double[]{3.7500, 8.7833}),
            Map.entry("Sao Tome", new double[]{0.3376, 6.7333}),
            Map.entry("Luanda", new double[]{-8.8147, 13.2344}),
            Map.entry("Brazzaville", new double[]{-4.2744, 15.2833}),
            Map.entry("Kinshasa", new double[]{-4.3033, 15.3119}),
            Map.entry("Bangui", new double[]{4.3622, 18.5833}),
            Map.entry("N'Djamena", new double[]{12.1191, 15.0500})
    );

    public Map<String, double[]> getCityHits() {
        return this.cityCoordinates;
    }

    public Set<String> getCityNames() {
        return this.cityCoordinates.keySet();
    }
}
