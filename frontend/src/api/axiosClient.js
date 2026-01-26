// src/api/axiosClient.js
import axios from 'axios';

const sever = 'https://shop-backend-6lun.onrender.com/api';
const local = 'http://localhost:8080/api';

const axiosClient = axios.create({
  baseURL: local, 
  headers: {
    'Content-Type': 'application/json',
  },
});

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