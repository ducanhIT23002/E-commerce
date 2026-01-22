import React, { useState } from 'react';
import { Form, Input, Button, Card, Typography, ConfigProvider, Alert, message } from 'antd';
import { UserOutlined, MailOutlined, LockOutlined } from '@ant-design/icons';
import { Link, useNavigate } from 'react-router-dom';
import authApi from '../../api/authApi';
import styles from './Register.module.scss'; 

const { Title, Text } = Typography;

const Register = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  const onFinish = async (values) => {
    setLoading(true);
    setErrorMsg('');
    
    try {
      await authApi.register({
        username: values.username,
        email: values.email,
        password: values.password
      });

      message.success('Registration successful! Redirecting to login...');
      setTimeout(() => {
        navigate('/login');
      }, 1500);

    } catch (error) {
      if (error.response && error.response.status === 409) {
        setErrorMsg(error.response.data); 
      } 
      else if (error.response && error.response.data) {
        setErrorMsg(typeof error.response.data === 'string' ? error.response.data : 'Registration failed');
      } 
      else {
        setErrorMsg("Server is not responding. Please try again later.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <ConfigProvider
      theme={{
        token: {
          colorPrimary: '#000000', 
          colorTextBase: '#333333',
          borderRadius: 6,
        },
      }}
    >
      <div className={styles.registerPage}>
        <Card className={styles.registerCard} bordered={false}>
          
          <div className={styles.headerSection}>
            <Title level={2} style={{ margin: 0 }}>PARKING SYSTEM</Title>
            <Text type="secondary">Create account to reserve your spot</Text>
          </div>

        
          {errorMsg && (
            <Alert 
              message={errorMsg} 
              type="error" 
              showIcon 
              closable 
              onClose={() => setErrorMsg('')}
              style={{ marginBottom: 20 }} 
            />
          )}

          <Form
            name="register_form"
            onFinish={onFinish}
            layout="vertical"
            size="large"
          >
            <Form.Item
              name="username"
              rules={[{ required: true, message: 'Please enter username' }]}
            >
              <Input prefix={<UserOutlined />} placeholder="Username" />
            </Form.Item>

            <Form.Item
              name="email"
              rules={[
                { required: true, message: 'Please enter email' },
                { type: 'email', message: 'Invalid email format' }
              ]}
            >
              <Input prefix={<MailOutlined />} placeholder="Email Address" />
            </Form.Item>

            <Form.Item
              name="password"
              rules={[{ required: true, message: 'Please enter password' }]}
            >
              <Input.Password prefix={<LockOutlined />} placeholder="Password" />
            </Form.Item>

            <Form.Item>
              <Button type="primary" htmlType="submit" block loading={loading} className={styles.submitBtn}>
                REGISTER
              </Button>
            </Form.Item>
          </Form>
          
          <div className={styles.footerSection}>
            <Text>Already have an account? </Text>
            <Link to="/login" className={styles.linkText}>
              Log in
            </Link>
          </div>
        </Card>
      </div>
    </ConfigProvider>
  );
};

export default Register;