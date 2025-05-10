const express = require('express');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const router = express.Router();

// Dummy in-memory users array for now
const users = [];

// Register (role-based)
router.post('/register', async (req, res) => {
  const { username, password, role } = req.body;

  // Ensure the user role is valid (either serviceProvider or customer)
  if (!['serviceProvider', 'customer'].includes(role)) {
    return res.status(400).json({ message: 'Invalid role. Choose "serviceProvider" or "customer".' });
  }

  // Hash the password before saving it
  const hashedPassword = await bcrypt.hash(password, 10);
  const user = { username, password: hashedPassword, role };
  users.push(user);

  res.status(201).json({ message: 'User registered successfully' });
});

// Login (returns a JWT with role-based info)
router.post('/login', async (req, res) => {
  const { username, password } = req.body;

  const user = users.find(u => u.username === username);
  if (!user) return res.status(400).json({ message: 'Invalid credentials' });

  // Compare password
  const isMatch = await bcrypt.compare(password, user.password);
  if (!isMatch) return res.status(400).json({ message: 'Invalid credentials' });

  // Generate JWT with the role of the user
  const token = jwt.sign(
    { username: user.username, role: user.role },
    'your-secret-key', // secret key to sign the token
    { expiresIn: '1h' } // token expiration time
  );

  res.json({ token });
});

// Middleware to check if user has the required role
const authorizeRole = (role) => {
  return (req, res, next) => {
    // Get the JWT token from the Authorization header
    const token = req.header('Authorization')?.replace('Bearer ', '');
    if (!token) return res.status(403).json({ message: 'Access denied. No token provided.' });

    // Verify the token
    try {
      const decoded = jwt.verify(token, 'your-secret-key');
      if (decoded.role !== role) {
        return res.status(403).json({ message: 'Access denied. Unauthorized role.' });
      }
      req.user = decoded; // Attach user info to request object
      next();
    } catch (err) {
      return res.status(400).json({ message: 'Invalid token.' });
    }
  };
}

// Example protected route for Service Providers
router.get('/service-provider-only', authorizeRole('serviceProvider'), (req, res) => {
  res.json({ message: 'Welcome, Service Provider!' });
});

// Example protected route for Customers
router.get('/customer-only', authorizeRole('customer'), (req, res) => {
  res.json({ message: 'Welcome, Customer!' });
});

module.exports = router;
