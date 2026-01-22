import React, { useState } from 'react';
import { Form, Input, Button, Card, Typography, ConfigProvider, Alert, message, Checkbox } from 'antd';
import { UserOutlined, LockOutlined, MailOutlined } from '@ant-design/icons'; // Thêm MailOutlined cho đẹp
import { Link, useNavigate } from 'react-router-dom';
import authApi from '../../api/authApi';
import styles from './Login.module.scss'; 

const { Title, Text } = Typography;

const Login = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  const onFinish = async (values) => {
    setLoading(true);
    setErrorMsg('');
    
    try {
      // 1. GỌI API LOGIN (Sửa lại key thành email)
      const response = await authApi.login({
        email: values.email,      // 👉 Đã đổi từ username sang email
        password: values.password
      });

      // 2. Xử lý thành công
      // Axios Interceptor của bạn đã trả về response.data, nên response ở đây là object chứa token
      if (response.token) {
        localStorage.setItem('access_token', response.token);
        localStorage.setItem('user', JSON.stringify(response)); 
        
        message.success('Welcome back ' + response.username + '!');
        
        // 3. Chuyển hướng
        setTimeout(() => {
          navigate('/'); 
        }, 1000);
      }

    } catch (error) {
      // 4. XỬ LÝ LỖI (Lấy message từ Backend trả về)
      // error.response.data chính là chuỗi: "Error: Invalid email or password!"
      const msg = error.response?.data || 'Login failed. Please try again later.';
      setErrorMsg(msg);
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
      <div className={styles.loginPage}>
        <Card className={styles.loginCard} bordered={false}>
          
          <div className={styles.headerSection}>
            <Title level={2} style={{ margin: 0 }}>WELCOME BACK</Title>
            <Text type="secondary">Please login to manage your parking</Text>
          </div>

          {errorMsg && (
            <Alert 
              message={errorMsg} 
              type="error" 
              showIcon 
              style={{ marginBottom: 20 }} 
            />
          )}

          <Form
            name="login_form"
            onFinish={onFinish}
            layout="vertical"
            size="large"
            initialValues={{ remember: true }}
          >
            {/* 👉 SỬA INPUT EMAIL */}
            <Form.Item
              name="email" // Key này phải khớp với values.email ở trên
              rules={[
                { required: true, message: 'Please input your Email!' },
                { type: 'email', message: 'Invalid email format!' } // Validate đúng chuẩn email
              ]}
            >
              {/* Dùng icon Mail nhìn cho hợp lý hơn */}
              <Input prefix={<MailOutlined />} placeholder="Email Address" />
            </Form.Item>

            <Form.Item
              name="password"
              rules={[{ required: true, message: 'Please input your Password!' }]}
            >
              <Input.Password prefix={<LockOutlined />} placeholder="Password" />
            </Form.Item>

            <div className={styles.optionsRow}>
              <Form.Item name="remember" valuePropName="checked" noStyle>
                <Checkbox>Remember me</Checkbox>
              </Form.Item>
              <a href="#" style={{ color: '#000000', fontWeight: 500 }}>Forgot password?</a>
            </div>

            <Form.Item style={{ marginTop: 20 }}>
              <Button type="primary" htmlType="submit" block loading={loading} className={styles.submitBtn}>
                LOGIN
              </Button>
            </Form.Item>
          </Form>
          
          <div className={styles.footerSection}>
            <Text>New to Parking System? </Text>
            <Link to="/register" className={styles.linkText}>
              Create an account
            </Link>
          </div>
        </Card>
      </div>
    </ConfigProvider>
  );
};

export default Login;