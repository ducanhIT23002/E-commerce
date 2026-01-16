import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [message, setMessage] = useState("Đang chờ tín hiệu từ Backend...");

  useEffect(() => {
    // Đây là link Backend của bạn trên Render
    const BACKEND_URL = "https://shop-backend-6lun.onrender.com/test";

    fetch(BACKEND_URL)
      .then(response => {
        if (!response.ok) {
          throw new Error('Không thể kết nối!');
        }
        return response.text();
      })
      .then(data => {
        // Nếu lấy được dữ liệu thì cập nhật lên màn hình
        setMessage(data);
      })
      .catch(error => {
        setMessage("Lỗi kết nối: " + error.message);
      });
  }, []);

  return (
    <div style={{ textAlign: 'center', marginTop: '50px', fontFamily: 'Arial' }}>
      <h1>TEST KẾT NỐI HỆ THỐNG</h1>
      
      <div style={{ 
        padding: '20px', 
        margin: '20px auto',
        maxWidth: '600px',
        border: '2px solid #4CAF50', 
        borderRadius: '10px',
        backgroundColor: '#f0fff0'
      }}>
        <h3>Thông điệp từ Server Java:</h3>
        <p style={{ 
          fontSize: '24px', 
          fontWeight: 'bold', 
          color: '#d32f2f' 
        }}>
          {message}
        </p>
      </div>
    </div>
  )
}

export default App