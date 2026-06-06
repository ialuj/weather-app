export const environment = {
  production: true,
  apiUrl:
    process.env['BACKEND_URL'] ||
    'https://weather-backend.containers.snapdeploy.dev/api',
};
