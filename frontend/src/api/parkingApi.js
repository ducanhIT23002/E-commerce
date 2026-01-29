import axiosClient from "./axiosClient";

const parkingApi = {
  getAll() {
    const url = '/parking-slots';
    return axiosClient.get(url);
  },

  create(data) {
    const url = '/parking-slots';
    return axiosClient.post(url, data);
  },


  updateStatus(id, status) {
    const url = `/parking-slots/${id}/status`;
    return axiosClient.put(url, null, {
      params: { status } 
    });
  },

  bookSlot(bookingData) {
    const url = '/bookings';
    return axiosClient.post(url, bookingData);
  },

  getMyBookings() {
    const url = '/bookings/my-bookings';
    return axiosClient.get(url);
  }
};

export default parkingApi;