import React, { useState } from "react";
import { Form, Input, Button, notification } from "antd";
import {
  UserOutlined,
  LockOutlined,
  ThunderboltTwoTone,
} from "@ant-design/icons";
import { login } from "../util/ApiUtil";
import "./Signin.css";


const Signin = (props) => {
  const [loading, setLoading] = useState(false);

  const onFinish = (values) => {
    setLoading(true);
    login(values)
      .then((response) => {
        localStorage.setItem("accessToken", response.accessToken);
        window.location.href = '/';
        setLoading(false);
      })
      .catch((error) => {
        if (error.status === 401) {
          notification.error({
            message: "Error",
            description: "用户名或密码错误，请重试!",
          });
        } else {
          notification.error({
            message: "Error",
            description:
              error.message || "抱歉! 登录错误. 请稍后重试!",
          });
        }
        setLoading(false);
      });
  };

  return (
    <div className="login-container">
      <ThunderboltTwoTone style={{ fontSize: 50 }} />
      <Form
        name="normal_login"
        className="login-form"
        initialValues={{ remember: true }}
        onFinish={onFinish}
      >
        <Form.Item
          name="username"
          rules={[{ required: true, message: "请输入用户名!" }]}
        >
          <Input
            size="large"
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="用户名"
          />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[{ required: true, message: "请输入密码!" }]}
        >
          <Input
            size="large"
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="密码"
          />
        </Form.Item>
        <Form.Item>
          <Button
            shape="round"
            size="large"
            htmlType="submit"
            className="login-form-button"
            loading={loading}
          >
            登录
          </Button>
        </Form.Item>
        未注册? <a href="/signup">注册</a>
      </Form>
    </div>
  );
};

export default Signin;
