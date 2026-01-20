// src/pages/Register/Register.jsx
import React, { useState } from 'react';
import { Link } from 'react-router-dom'; 
import authApi from '../../api/authApi';
import styles from './Register.module.scss';

const Register = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: ''
  });
  const [message, setMessage] = useState('');
  const [isError, setIsError] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    
    try {
      const response = await authApi.register(formData);
      setMessage(`Registration successful! User ID: ${response.id}`);
      setIsError(false);
      setFormData({ username: '', email: '', password: '' });
      
    } catch (error) {
      setIsError(true);
      if (error.response && error.response.data) {
        setMessage(error.response.data); 
      } else {
        setMessage("Connection error or Server not responding.");
      }
    }
  };

  return (
    <div className={styles.registerPage}>
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-11 col-sm-10 col-md-8 col-lg-5">
            <div className={`card shadow ${styles.card}`}>
              <div className={styles.cardHeader}>
                Create Account
              </div>
              <div className="card-body p-4">
                
                {message && (
                  <div className={`alert ${isError ? 'alert-danger' : 'alert-success'} text-center`}>
                    {message}
                  </div>
                )}

                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label className="form-label">Username</label>
                    <input 
                      type="text" 
                      className="form-control" 
                      name="username"
                      value={formData.username} 
                      onChange={handleChange} 
                      placeholder="Enter your username"
                      required 
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Email Address</label>
                    <input 
                      type="email" 
                      className="form-control" 
                      name="email"
                      value={formData.email} 
                      onChange={handleChange} 
                      placeholder="name@example.com"
                      required 
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Password</label>
                    <input 
                      type="password" 
                      className="form-control" 
                      name="password"
                      value={formData.password} 
                      onChange={handleChange} 
                      placeholder="Create a password"
                      required 
                    />
                  </div>

                  <button type="submit" className="btn btn-primary w-100 py-2">
                    Register
                  </button>
                </form>

                <div className="text-center mt-3">
                  <small>
                    Already have an account? <Link to="/login">Log in</Link>
                  </small>
                </div>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;