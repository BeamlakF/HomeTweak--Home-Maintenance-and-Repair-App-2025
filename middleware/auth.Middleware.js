const jwt = require('jsonwebtoken');

// Authentication middleware
const authMiddleware = (req, res, next) => {
  const token = req.header('Authorization')?.replace('Bearer ', '');

  if (!token) {
    return res.status(401).json({ message: 'No token, authorization denied' });
  }

  try {
    const decoded = jwt.verify(token, process.env.JWT_SECRET);
    req.user = decoded; // decoded contains { email, role }
    next();
  } catch (err) {
    return res.status(401).json({ message: 'Invalid token' });
  }
};

// Role-based authorization
const authorizeRoles = (...allowedRoles) => {
  return (req, res, next) => {
    if (!req.user || !allowedRoles.includes(req.user.role)) {
      return res.status(403).json({ message: 'Access denied: unauthorized role' });
    }
    next();
  };
};

module.exports = { authMiddleware, authorizeRoles };
