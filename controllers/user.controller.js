const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const users = require("../models/user");

exports.register = async (req, res) => {
  const { username, password, role } = req.body;

  const existingUser = users.find(u => u.username === username);
  if (existingUser) return res.status(400).json({ message: "User already exists" });

  const hashedPassword = await bcrypt.hash(password, 10);
  const user = { id: users.length + 1, username, password: hashedPassword, role };
  users.push(user);

  res.status(201).json({ message: "User registered successfully" });
};

exports.login = async (req, res) => {
  const { username, password } = req.body;
  const user = users.find(u => u.username === username);
  if (!user) return res.status(400).json({ message: "User not found" });

  const isMatch = await bcrypt.compare(password, user.password);
  if (!isMatch) return res.status(401).json({ message: "Invalid password" });

  const token = jwt.sign({ id: user.id, role: user.role }, process.env.JWT_SECRET, { expiresIn: "1h" });

  res.json({ token });
};

// Delete a user by ID
exports.deleteUser = async (req, res) => {
  try {
      const { id } = req.params;

      const user = await User.findByPk(id);
      if (!user) {
          return res.status(404).json({ message: 'User not found' });
      }

      await user.destroy();
      res.status(200).json({ message: 'User account deleted successfully' });
  } catch (error) {
      res.status(500).json({ error: error.message });
  }
};
