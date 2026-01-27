// src/api/axiosClient.js
import axios from 'axios';

const serverUrl = 'https://shop-backend-6lun.onrender.com/api';
const localUrl = 'http://localhost:8080/api';

const axiosClient = axios.create({
  baseURL: localUrl, 
  headers: {
    'Content-Type': 'application/json',
  },
});


axiosClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('access_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosClient.interceptors.response.use(
  (response) => {
    return response.data; 
  },
  (error) => {
    console.error('API Error:', error.response || error.message);
    return Promise.reject(error);
  }
);

export default axiosClient;