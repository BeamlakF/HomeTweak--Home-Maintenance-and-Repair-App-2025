// const mongoose = require('mongoose');

// const UserSchema = new mongoose.Schema({
//   username: {
//     type: String,
//     required: true,
//     unique: true,
//   },
//   password: {
//     type: String,
//     required: true,
//   },
//   role: {
//     type: String,
//     enum: ['service-provider', 'customer'],
//     required: true,
//   },
// });

// module.exports = mongoose.model('User', UserSchema);

const users = []; // in-memory store (for now)

module.exports = users;