import React, { useEffect, useState } from "react";
import { Form, Input, Button, notification } from "antd";
import { ThunderboltTwoTone } from "@ant-design/icons";
import { signup } from "../util/ApiUtil";
import "./Signup.css";

const Signup = (props) => {
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (localStorage.getItem("accessToken") !== null) {
      window.location.href = '/';
    }
  }, []);

  const onFinish = (values) => {
    setLoading(true);
    signup(values)
      .then((response) => {
        notification.success({
          message: "Success",
          description:
            "感谢使用! 您已成功注册. 请登录使用!",
        });
        props.history.push("/signin");
        setLoading(false);
      })
      .catch((error) => {
        notification.error({
          message: "Error",
          description:
            error.message || "抱歉! 注册错误. 请稍后重试!",
        });
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
          <Input size="large" placeholder="用户名" />
        </Form.Item>
        <Form.Item
          name="email"
          rules={[{ required: true, message: "请输入邮箱!" }]}
        >
          <Input size="large" placeholder="邮箱" />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[{ required: true, message: "请输入密码!" }]}
        >
          <Input size="large" type="password" placeholder="密码" />
        </Form.Item>
        <Form.Item>
          <Button
            shape="round"
            size="large"
            htmlType="submit"
            className="login-form-button"
            loading={loading}
          >
            注册
          </Button>
        </Form.Item>
        已注册? <a href="/signin">登录</a>
      </Form>
    </div>
  );
};

export default Signup;
