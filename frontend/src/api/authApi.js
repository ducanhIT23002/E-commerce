// src/api/authApi.js
import axiosClient from "./axiosClient";

const authApi = {
  register(data) {
    // Gọi đến đường dẫn /auth/register
    // axiosClient đã lo phần http://localhost:8080/api rồi
    return axiosClient.post('/auth/register', data);
  },
  
  // Sau này có login thì thêm vào đây:
  // login(data) { return axiosClient.post('/auth/login', data); }
};

export default authApi;