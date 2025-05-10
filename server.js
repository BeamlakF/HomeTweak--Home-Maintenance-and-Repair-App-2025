const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');
const authRoutes = require('./routes/auth');

dotenv.config();
const app = express();

// Middleware
app.use(express.json());  // Body parser for JSON
app.use(cors());          // Enable CORS for all routes

// Routes
app.use('/api/auth', authRoutes);

const PORT = process.env.PORT ||5000;
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
