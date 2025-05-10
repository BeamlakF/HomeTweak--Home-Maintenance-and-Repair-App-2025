const { DataTypes } = require('sequelize');
const sequelize = require('../config/db');

const User = sequelize.define('User', {
  email: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false
  },
  role: {
    type: DataTypes.ENUM('customer', 'serviceProvider'),
    allowNull: false
  },
  resetCode: {
    type: DataTypes.STRING,
    allowNull: true
  }
});

module.exports = User;
