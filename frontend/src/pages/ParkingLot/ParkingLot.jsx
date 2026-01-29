import React, { useEffect, useState } from 'react';
import { Card, Typography, Row, Col, Spin, Tag, message, Button, Modal, Form, Input, InputNumber } from 'antd';
import { CarOutlined, ReloadOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import parkingApi from '../../api/parkingApi'; 
import styles from './ParkingLot.module.scss'; 

const { Title, Text } = Typography;

const ParkingLot = () => {
  const [slots, setSlots] = useState([]);
  const [loading, setLoading] = useState(true);
  const [bookingModalVisible, setBookingModalVisible] = useState(false);
  const [selectedSlot, setSelectedSlot] = useState(null);
  const [bookingLoading, setBookingLoading] = useState(false);
  const [form] = Form.useForm();

  const navigate = useNavigate();

  const fetchSlots = async () => {
    setLoading(true);
    try {
      const response = await parkingApi.getAll();
      
      if (response && response.data) {
        setSlots(response.data.data || response.data);
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

  console.log('Slots data:', slots);

  const formatDateTime = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  };


  const handleSlotClick = (slot) => {
    const token = localStorage.getItem('access_token');
    
    if (!token) {
      Modal.confirm({
        title: 'Login Required',
        content: 'You need to login to book a parking slot.',
        okText: 'Go to Login',
        cancelText: 'Cancel',
        onOk: () => navigate('/login'),
      });
      return;
    }

    if (slot.status !== 'AVAILABLE') {
      message.warning('This parking slot is not available.');
      return;
    }

    setSelectedSlot(slot);
    setBookingModalVisible(true);
    form.resetFields();
  };


  const handleBookingSubmit = async (values) => {
    if (!selectedSlot) return;

    setBookingLoading(true);
    try {
   
      const now = new Date();
      const startTime = formatDateTime(now);
      
      const endTime = new Date(now.getTime() + values.duration * 60 * 60 * 1000);
      const endTimeFormatted = formatDateTime(endTime);

      const bookingData = {
        slotId: selectedSlot.id,
        startTime: startTime,
        endTime: endTimeFormatted,
        licensePlate: values.licensePlate,
        vehicleId: null 
      };

      console.log('Booking data:', bookingData);

      const response = await parkingApi.bookSlot(bookingData);

      if (response.status === 201 || response.data.status === 201) {
        message.success('Booking successful!');
        
        setSlots(prevSlots => 
          prevSlots.map(s => 
            s.id === selectedSlot.id ? { ...s, status: 'BOOKED' } : s
          )
        );

        setBookingModalVisible(false);
        form.resetFields();
        setSelectedSlot(null);

        fetchSlots();
      }
    } catch (error) {
      console.error('Booking error:', error);
      const errorMessage = error.response?.data?.message || 'Booking failed. Please try again.';
      message.error(errorMessage);
    } finally {
      setBookingLoading(false);
    }
  };

  const handleModalClose = () => {
    setBookingModalVisible(false);
    form.resetFields();
    setSelectedSlot(null);
  };

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
                  hoverable={isAvailable}
                  onClick={() => handleSlotClick(slot)}
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

      <Modal
        title={`Book Parking Slot: ${selectedSlot?.name}`}
        open={bookingModalVisible}
        onCancel={handleModalClose}
        footer={null}
        width={500}
      >
        <Form
          form={form}
          layout="vertical"
          onFinish={handleBookingSubmit}
          autoComplete="off"
        >
          <Form.Item
            label="License Plate"
            name="licensePlate"
            rules={[
              { required: true, message: 'Please enter your license plate!' },
              { min: 3, message: 'License plate must be at least 3 characters!' }
            ]}
          >
            <Input 
              placeholder="e.g., ABC-1234" 
              maxLength={20}
            />
          </Form.Item>

          <Form.Item
            label="Duration (hours)"
            name="duration"
            rules={[
              { required: true, message: 'Please enter booking duration!' },
              { 
                pattern: /^[1-9]\d*$/, 
                message: 'Duration must be a positive number!' 
              }
            ]}
          >
            <InputNumber 
              min={1} 
              max={24}
              step={1}
              placeholder="e.g., 2"
              style={{ width: '100%' }}
            />
          </Form.Item>

          <Form.Item shouldUpdate={(prevValues, curValues) => prevValues.duration !== curValues.duration}>
            {() => {
              const duration = form.getFieldValue('duration');
              return duration ? (
                <Text type="secondary" style={{ display: 'block', marginTop: 10 }}>
                  <div>Start: {new Date().toLocaleString()}</div>
                  <div>
                    End: {new Date(new Date().getTime() + duration * 60 * 60 * 1000).toLocaleString()}
                  </div>
                  <div style={{ color: '#faad14' }}>Cost: {(duration * 50000).toLocaleString()} VND</div>
                </Text>
              ) : null;
            }}
          </Form.Item>

          <Form.Item>
            <Button 
              type="primary" 
              htmlType="submit" 
              block 
              loading={bookingLoading}
              style={{ marginBottom: '8px' }}
            >
              Confirm Booking
            </Button>
            <Button 
              block 
              onClick={handleModalClose}
            >
              Cancel
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ParkingLot;