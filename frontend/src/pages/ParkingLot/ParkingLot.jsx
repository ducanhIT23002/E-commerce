import React, { useEffect, useState } from 'react';
import { Card, Typography, Row, Col, Spin, Tag, message, Button } from 'antd';
import { CarOutlined, ReloadOutlined } from '@ant-design/icons';
import parkingApi from '../../api/parkingApi'; 
import styles from './ParkingLot.module.scss'; 

const { Title, Text } = Typography;

const ParkingLot = () => {
  const [slots, setSlots] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchSlots = async () => {
    setLoading(true);
    try {
      const response = await parkingApi.getAll();
      
      if (response && response.data) {
        setSlots(response.data);
      }
    } catch (error) {
      message.error('Failed to load parking slots');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSlots();
  }, []);
  return (
    <div className={styles.parkingContainer}>

      <div className={styles.header}>
        <div>
          <Title level={2} style={{ marginBottom: 0 }}>Parking Lot Status</Title>
          <Text type="secondary">Real-time availability updates</Text>
        </div>
        <Button icon={<ReloadOutlined />} onClick={fetchSlots}>Refresh</Button>
      </div>


      <div className={styles.legend}>
        <div className={styles.legendItem}>
          <span className={`${styles.dot} ${styles.available}`}></span> Available
        </div>
        <div className={styles.legendItem}>
          <span className={`${styles.dot} ${styles.booked}`}></span> Booked
        </div>
      </div>

      {loading ? (
        <div className={styles.loadingState}>
          <Spin size="large" tip="Loading slots..." />
        </div>
      ) : (
        <Row gutter={[24, 24]} className={styles.gridContainer}>
          {slots.map((slot) => {
            const isAvailable = slot.status === 'AVAILABLE';
            
            return (
              <Col xs={12} sm={8} md={6} lg={4} key={slot.id}>
                <Card 
                  className={`${styles.slotCard} ${isAvailable ? styles.cardAvailable : styles.cardBooked}`}
                  bordered={false}
                  hoverable
                >
                  <div className={styles.slotContent}>
                    <div className={styles.slotName}>{slot.name}</div>
                    
                    <CarOutlined className={styles.carIcon} />
                    
                    <Tag color={isAvailable ? 'success' : 'error'} style={{ marginTop: 10 }}>
                      {slot.status}
                    </Tag>
                  </div>
                </Card>
              </Col>
            );
          })}
        </Row>
      )}
    </div>
  );
};

export default ParkingLot;