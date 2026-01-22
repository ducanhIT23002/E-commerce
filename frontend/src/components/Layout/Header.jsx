import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { Button, ConfigProvider } from 'antd';
import { CarOutlined, LoginOutlined, UserAddOutlined } from '@ant-design/icons';
import styles from './Header.module.scss';

const Header = () => {
  const location = useLocation();

  const isLoginPage = location.pathname === '/login';
  const isRegisterPage = location.pathname === '/register';

  return (

    <ConfigProvider
      theme={{
        token: {
          colorPrimary: '#ffffff', 
          colorTextBase: '#ffffff',
        },
      }}
    >
      <header className={styles.headerWrapper}>
        <div className="container h-100">
          <div className={styles.headerContent}>
            

            <Link to="/" className={styles.logoSection}>
              <CarOutlined className={styles.logoIcon} />
              <span className={styles.logoText}>PARKING SYSTEM</span>
            </Link>


            <div className={styles.navActions}>
              

              {!isLoginPage && (
                <Link to="/login">
                  <Button 
                    type="text" 
                    icon={<LoginOutlined />}
                    className={styles.ghostBtn}
                  >
                    Login
                  </Button>
                </Link>
              )}


              {!isRegisterPage && (
                <Link to="/register">
                  <Button 
                    type="primary" 
                    icon={<UserAddOutlined />}
                    className={styles.primaryBtn}
                  >
                    Register
                  </Button>
                </Link>
              )}
            </div>

          </div>
        </div>
      </header>
    </ConfigProvider>
  );
};

export default Header;