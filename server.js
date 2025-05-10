const express = require('express');
const dotenv = require('dotenv');
const { sequelize } = require('./models/index');

// Import routes
const userRoutes = require('./routes/user.routes');

dotenv.config();

const app = express();

app.use(express.json());

// Correct route usage
app.use('/api/users', userRoutes);

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});

sequelize.authenticate()
    .then(() => {
        console.log('Database connected...');
        return sequelize.sync();
    })
    .then(() => {
        console.log('Models synced!');
    })
    .catch(err => {
        console.error('Error connecting to the database:', err);
    });