import React, { useState } from "react";
import "./Login.css";
import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";
import { BsSun, BsMoon } from "react-icons/bs";
import api from "./api";

export default function Login() {
  // ------------------ STATE ------------------
  const [form, setForm] = useState(() => {
    const rememberedUser = localStorage.getItem("rememberedUser") || "";
    return { username: rememberedUser, password: "" };
  });
  const [errors, setErrors] = useState({});
  const [showPassword, setShowPassword] = useState(false);
  const [theme, setTheme] = useState(() => {
    const saved = localStorage.getItem("theme");
    document.documentElement.setAttribute("data-theme", saved || "dark");
    return saved || "dark";
  });
  const [rememberMe, setRememberMe] = useState(() => !!localStorage.getItem("rememberedUser"));
  const [isFocused, setIsFocused] = useState(false); // dùng để tạm pause animation

  // ------------------ VALIDATE ------------------
  function validateField(name, value) {
    const newErrors = { ...errors };
    if (name === "username") {
      if (!value.trim()) newErrors.username = "Username or Email is required.";
      else if (value.includes("@")) {
        const emailRegex = /\S+@\S+\.\S+/;
        if (!emailRegex.test(value)) newErrors.username = "Invalid email format.";
        else delete newErrors.username;
      } else delete newErrors.username;
    }
    if (name === "password") {
      if (!value.trim()) newErrors.password = "Password is required.";
      else if (value.length < 6) newErrors.password = "Password must be at least 6 characters.";
      else delete newErrors.password;
    }
    setErrors(newErrors);
  }

  function handleChange(e) {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
    validateField(name, value);
  }

function handleSubmit(e) {
  e.preventDefault();
  validateField("username", form.username);
  validateField("password", form.password);

  if (Object.keys(errors).length === 0 && form.username && form.password) {
    // gọi backend
    api.post("/auth/login", {
      username: form.username,
      password: form.password,
    })
      .then((res) => {
        console.log("Login success:", res.data);

        // Lưu token nếu dùng JWT
        localStorage.setItem("accessToken", res.data.accessToken);
        localStorage.setItem("refreshToken", res.data.refreshToken);

        if (rememberMe) localStorage.setItem("rememberedUser", form.username);
        else localStorage.removeItem("rememberedUser");
      })
      .catch((err) => {
        console.error("Login failed:", err.response?.data?.message || err.message);
        alert(err.response?.data?.message || "Login failed");
      });
  }
}

  // ------------------ THEME TOGGLE ------------------
  function toggleTheme() {
    const newTheme = theme === "dark" ? "light" : "dark";
    setTheme(newTheme);
    document.documentElement.setAttribute("data-theme", newTheme);
    localStorage.setItem("theme", newTheme);
  }

  return (
    <div
      className={`login-page ${isFocused ? "paused" : ""}`}
      onFocus={() => setIsFocused(true)}
      onBlur={() => setIsFocused(false)}
    >
      {/* HEADER */}
      <header className="login-header">
        <div className="brand modern-brand">
          <div className="logo-circle">
            <div className="logo-inner"></div>
          </div>
          <div className="brand-text">
            <div className="brand-title">InfraCorp</div>
            <div className="brand-sub">Infrastructure Management Portal</div>
          </div>
        </div>
        <button className="theme-toggle" onClick={toggleTheme}>
          {theme === "dark" ? <BsSun size={22} /> : <BsMoon size={22} />}
        </button>
      </header>

      {/* FORM */}
      <main className="login-main">
        <form className="login-card" onSubmit={handleSubmit}>
          <h2 className="title">Welcome Back</h2>
          <p className="muted">Sign in to continue to your dashboard.</p>

          {/* USERNAME */}
          <label className="label">Username or Email</label>
          <div className={`input-wrap ${errors.username ? "has-error" : ""}`}>
            <input
              name="username"
              type="text"
              placeholder="Enter your username or email"
              value={form.username}
              onChange={handleChange}
            />
          </div>
          {errors.username && <p className="error-text">{errors.username}</p>}

          {/* PASSWORD */}
          <label className="label">Password</label>
          <div className={`input-wrap ${errors.password ? "has-error" : ""}`}>
            <input
              name="password"
              type={showPassword ? "text" : "password"}
              placeholder="Enter your password"
              value={form.password}
              onChange={handleChange}
            />
            <button
              type="button"
              className="eye-btn"
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? <AiOutlineEyeInvisible size={20} /> : <AiOutlineEye size={20} />}
            </button>
          </div>
          {errors.password && <p className="error-text">{errors.password}</p>}

          {/* REMEMBER ME */}
          <div className="remember-me">
            <input
              id="rememberMe"
              type="checkbox"
              checked={rememberMe}
              onChange={() => setRememberMe(!rememberMe)}
            />
            <label htmlFor="rememberMe">Remember Me</label>
          </div>

          <button className="primary" type="submit">
            Log In
          </button>

          <div className="login-footer">
            <a href="#">Forgot Password?</a>
          </div>
        </form>
      </main>

      <footer className="site-footer">© 2024 InfraCorp • Version 1.0.0</footer>
    </div>
  );
}
