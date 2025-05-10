const express = require('express');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const router = express.Router();

const users = [];

// Register
router.post('/register', async (req, res) => {
  const { email, password, role } = req.body;

  if (!['serviceProvider', 'customer'].includes(role)) {
    return res.status(400).json({ message: 'Invalid role. Choose "serviceProvider" or "customer".' });
  }

  const existingUser = users.find(u => u.email === email);
  if (existingUser) return res.status(400).json({ message: 'User already exists' });

  const hashedPassword = await bcrypt.hash(password, 10);
  const user = { email, password: hashedPassword, role, resetCode: null };
  users.push(user);

  res.status(201).json({ message: 'User registered successfully' });
});

// Login
router.post('/login', async (req, res) => {
  const { email, password } = req.body;

  const user = users.find(u => u.email === email);
  if (!user) return res.status(400).json({ message: 'Invalid credentials' });

  const isMatch = await bcrypt.compare(password, user.password);
  if (!isMatch) return res.status(400).json({ message: 'Invalid credentials' });

  const token = jwt.sign({ email: user.email, role: user.role }, 'your-secret-key', { expiresIn: '1h' });

  res.json({ token });
});

// Forgot Password
router.post('/forgot-password', (req, res) => {
  const { email } = req.body;
  const user = users.find(u => u.email === email);
  if (!user) return res.status(404).json({ message: 'User not found' });

  const resetCode = Math.floor(100000 + Math.random() * 900000).toString();
  user.resetCode = resetCode;

  console.log(`Reset code for ${email}: ${resetCode}`); // Simulate sending

  res.json({ message: 'Reset code sent to your email' });
});

// Reset Password
router.post('/reset-password', async (req, res) => {
  const { email, resetCode, newPassword } = req.body;
  const user = users.find(u => u.email === email);

  if (!user || user.resetCode !== resetCode) {
    return res.status(400).json({ message: 'Invalid reset code' });
  }

  user.password = await bcrypt.hash(newPassword, 10);
  user.resetCode = null;

  res.json({ message: 'Password reset successful' });
});

// Role-based middleware
const authorizeRole = (role) => {
  return (req, res, next) => {
    const token = req.header('Authorization')?.replace('Bearer ', '');
    if (!token) return res.status(403).json({ message: 'Access denied. No token provided.' });

    try {
      const decoded = jwt.verify(token, 'your-secret-key');
      if (decoded.role !== role) {
        return res.status(403).json({ message: 'Access denied. Unauthorized role.' });
      }
      req.user = decoded;
      next();
    } catch (err) {
      return res.status(400).json({ message: 'Invalid token.' });
    }
  };
};

// Protected routes
router.get('/service-provider-only', authorizeRole('serviceProvider'), (req, res) => {
  res.json({ message: 'Welcome, Service Provider!' });
});

router.get('/customer-only', authorizeRole('customer'), (req, res) => {
  res.json({ message: 'Welcome, Customer!' });
});

module.exports = router;

const express = require('express');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const User = require('./models/User');
const router = express.Router();

// Register
router.post('/register', async (req, res) => {
  const { email, password, role } = req.body;

  if (!['customer', 'serviceProvider'].includes(role)) {
    return res.status(400).json({ message: 'Invalid role' });
  }

  const existingUser = await User.findOne({ where: { email } });
  if (existingUser) return res.status(400).json({ message: 'User already exists' });

  const hashedPassword = await bcrypt.hash(password, 10);
  await User.create({ email, password: hashedPassword, role });

  res.status(201).json({ message: 'User registered successfully' });
});

// Login
router.post('/login', async (req, res) => {
  const { email, password } = req.body;

  const user = await User.findOne({ where: { email } });
  if (!user) return res.status(400).json({ message: 'Invalid credentials' });

  const isMatch = await bcrypt.compare(password, user.password);
  if (!isMatch) return res.status(400).json({ message: 'Invalid credentials' });

  const token = jwt.sign({ email: user.email, role: user.role }, process.env.JWT_SECRET, { expiresIn: '1h' });

  res.json({ token });
});

// Forgot Password
router.post('/forgot-password', async (req, res) => {
  const { email } = req.body;

  const user = await User.findOne({ where: { email } });
  if (!user) return res.status(404).json({ message: 'User not found' });

  const code = Math.floor(100000 + Math.random() * 900000).toString();
  await user.update({ resetCode: code });

  console.log(`Reset code for ${email}: ${code}`);
  res.json({ message: 'Reset code sent' });
});

// Reset Password
router.post('/reset-password', async (req, res) => {
  const { email, resetCode, newPassword } = req.body;

  const user = await User.findOne({ where: { email, resetCode } });
  if (!user) return res.status(400).json({ message: 'Invalid code or email' });

  const hashed = await bcrypt.hash(newPassword, 10);
  await user.update({ password: hashed, resetCode: null });

  res.json({ message: 'Password reset successful' });
});

const { authMiddleware, authorizeRoles } = require('./middleware/auth');

// Protected route for Service Providers
router.get('/service-provider-only', authMiddleware, authorizeRoles('serviceProvider'), (req, res) => {
  res.json({ message: `Welcome, Service Provider ${req.user.email}!` });
});

// Protected route for Customers
router.get('/customer-only', authMiddleware, authorizeRoles('customer'), (req, res) => {
  res.json({ message: `Welcome, Customer ${req.user.email}!` });
});


module.exports = router;

