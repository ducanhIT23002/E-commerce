// src/routes/AppRoutes.jsx
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Register from '../pages/Register/Register';
import Login from '../pages/Login/Login'; 
import Home from '../pages/Home/Home';
const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      
      <Route path="/register" element={<Register />} />

      <Route path="/login" element={<Login />} />
      
    </Routes>
  );
};

export default AppRoutes;