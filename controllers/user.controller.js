const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const users = require("../models/User");

exports.register = async (req, res) => {
  const { email, password, role } = req.body;

  const existingUser = users.find(u => u.email === email);
  if (existingUser) return res.status(400).json({ message: "User already exists" });

  const hashedPassword = await bcrypt.hash(password, 10);
  const user = { id: users.length + 1, email, password: hashedPassword, role };
  users.push(user);

  res.status(201).json({ message: "User registered successfully" });
};

exports.login = async (req, res) => {
  const { email, password } = req.body;
  const user = users.find(u => u.email === email);
  if (!user) return res.status(400).json({ message: "User not found" });

  const isMatch = await bcrypt.compare(password, user.password);
  if (!isMatch) return res.status(401).json({ message: "Invalid password" });

  const token = jwt.sign({ id: user.id, role: user.role }, process.env.JWT_SECRET, { expiresIn: "1h" });

  res.json({ token });
};

exports.forgotPassword = (req, res) => {
  const { email } = req.body;
  const user = users.find(u => u.email === email);

  if (!user) return res.status(404).json({ message: "User not found" });

  const code = Math.floor(100000 + Math.random() * 900000).toString(); // 6-digit code
  user.resetCode = code;

  console.log(`Reset code for ${email}: ${code}`); // Simulate sending email

  res.json({ message: "Reset code sent to email" });
};

exports.resetPassword = async (req, res) => {
  const { email, resetCode, newPassword } = req.body;

  const user = users.find(u => u.email === email);
  if (!user || user.resetCode !== resetCode) {
    return res.status(400).json({ message: "Invalid email or code" });
  }

  user.password = await bcrypt.hash(newPassword, 10);
  user.resetCode = null; // Clear reset code

  res.json({ message: "Password reset successful" });
};

