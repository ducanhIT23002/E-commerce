import React, { useEffect, useState } from 'react';
import { Button, Card, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';

const { Title, Text } = Typography;

const Home = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const userData = localStorage.getItem('user');
    if (userData) {
      setUser(JSON.parse(userData));
    } else {
      navigate('/login');
    }
  }, [navigate]);

  const handleLogout = () => {

    localStorage.removeItem('access_token');
    localStorage.removeItem('user');

    navigate('/login');
  };

  return (
    <div style={{ 
      display: 'flex', 
      justifyContent: 'center', 
      alignItems: 'center', 
      height: '100vh', 
      backgroundColor: '#f0f2f5' 
    }}>
      <Card style={{ width: 400, textAlign: 'center' }} bordered={false}>
        <Title level={2}>🏡 Home</Title>
        <Text type="secondary">Login Success</Text>
        
        <div style={{ margin: '30px 0', padding: '15px', background: '#f9f9f9', borderRadius: '8px' }}>
          <Text strong>Hello</Text>
          <br />

          <Title level={4} style={{ marginTop: 5, color: '#1890ff' }}>
            {user?.email || 'User'}
          </Title>
          <Text type="secondary">Role: {user?.roles || 'Member'}</Text>
        </div>

        <Button type="primary" danger size="large" onClick={handleLogout} block>
          Logout
        </Button>
      </Card>
    </div>
  );
};

export default Home;