// src/routes/AppRoutes.jsx
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Register from '../pages/Register/Register';
import Login from '../pages/Login/Login'; 
import Home from '../pages/Home/Home';
import ParkingLot from '../pages/ParkingLot/ParkingLot'; 
const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      
      <Route path="/register" element={<Register />} />

      <Route path="/login" element={<Login />} />
      
      <Route path="/parking" element={<ParkingLot />} />
    </Routes>
  );
};

export default AppRoutes;