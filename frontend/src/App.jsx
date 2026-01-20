// src/App.jsx
import React from 'react';
import MainLayout from './components/Layout/MainLayout';
import AppRoutes from './routes/AppRoutes'; 

function App() {
  return (
    <MainLayout>
      <AppRoutes /> 
    </MainLayout>
  );
}

export default App;