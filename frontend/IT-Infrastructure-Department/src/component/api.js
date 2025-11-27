import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8081/api", // URL backend
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true, // nếu backend dùng cookie
});

export default api;
