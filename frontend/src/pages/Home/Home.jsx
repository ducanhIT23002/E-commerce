import React, { useEffect, useState } from 'react';
import { Button, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import { CarOutlined } from '@ant-design/icons';
import styles from './Home.module.scss';

const { Title, Text } = Typography;

const Home = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const userData = localStorage.getItem('user');
    if (userData) {
      setUser(JSON.parse(userData));
    }
  }, []);

  return (
    <div className={styles.homePage}>
      <div className={styles.heroSection}>
        <div className={styles.content}>
          <Title level={1} style={{ color: 'white' }}>Find The Best Parking Spot Near You</Title>
          <Text style={{ color: 'rgba(255, 255, 255, 0.9)', fontSize: 18, display: 'block', marginBottom: 30 }}>
            Secure, automated, and hassle-free parking management system. 
            Check real-time availability and book your spot in seconds.
          </Text>
          
          <div className={styles.ctaButtons}>
            <Button 
              type="primary" 
              size="large" 
              shape="round" 
              icon={<CarOutlined />}
              style={{ height: 50, padding: '0 40px', fontSize: 18 }}
              onClick={() => navigate('/parking')} 
            >
              Find Parking Spot
            </Button>
            
            {!user && (
               <Button 
               size="large" 
               shape="round" 
               ghost 
               style={{ height: 50, padding: '0 40px', fontSize: 18, color: 'white', borderColor: 'white' }}
               onClick={() => navigate('/register')}
             >
               Join Now
             </Button>
            )}
          </div>
        </div>
      </div>


    </div>
  );
};

export default Home;