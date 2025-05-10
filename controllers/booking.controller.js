const { Booking } = require('../models');

// Create a booking (Service Booking)
exports.createBooking = async (req, res) => {
    try {
        const newBooking = await Booking.create(req.body);
        res.status(201).json(newBooking);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

// Get bookings for a provider (Viewing Bookings)
exports.getBookingsByProvider = async (req, res) => {
    try {
        const providerId = req.params.providerId;
        const bookings = await Booking.findAll({
            where: { providerId }
        });
        res.status(200).json(bookings);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

// Get bookings for a user (Optional: if user wants to see their bookings)
exports.getBookingsByUser = async (req, res) => {
    try {
        const userId = req.params.userId;
        const bookings = await Booking.findAll({
            where: { userId }
        });
        res.status(200).json(bookings);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

// Update booking status (Update Booking Status)
exports.updateBookingStatus = async (req, res) => {
    try {
        const { id } = req.params;
        const { status } = req.body;
        const [updated] = await Booking.update({ status }, { where: { id } });
        if (updated) {
            const updatedBooking = await Booking.findByPk(id);
            return res.status(200).json(updatedBooking);
        }
        throw new Error('Booking not found');
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

// Cancel booking (Cancel Booking)
exports.cancelBooking = async (req, res) => {
    try {
        const { id } = req.params;
        const [updated] = await Booking.update({ status: 'Cancelled' }, { where: { id } });
        if (updated) {
            const updatedBooking = await Booking.findByPk(id);
            return res.status(200).json(updatedBooking);
        }
        throw new Error('Booking not found');
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};
