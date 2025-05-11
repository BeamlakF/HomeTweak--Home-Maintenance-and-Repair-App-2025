const { DataTypes } = require('sequelize');
const sequelize = require('../config/db'); // your Sequelize instance

const User = sequelize.define('User', {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true
    },
    username: {
        type: DataTypes.STRING,
        allowNull: false
    },
    email: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true
    },
    password: {
        type: DataTypes.STRING,
        allowNull: false
    }
}, {
    tableName: 'users',  // optional: sets your table name
    timestamps: true     // adds createdAt and updatedAt
});

module.exports = User;
