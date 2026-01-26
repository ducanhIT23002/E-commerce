import axiosClient from "./axiosClient";

const parkingApi = {
  // 1. Lấy danh sách tất cả chỗ đỗ (GET /api/parking-slots)
  getAll() {
    const url = '/parking-slots';
    return axiosClient.get(url);
  },

  // 2. Tạo chỗ đỗ mới (POST /api/parking-slots) -> Dùng khi bạn muốn test tạo thêm
  create(data) {
    const url = '/parking-slots';
    return axiosClient.post(url, data);
  },

  // 3. Cập nhật trạng thái (PUT /api/parking-slots/{id}/status)
  // Ví dụ: updateStatus(1, 'BOOKED')
  updateStatus(id, status) {
    const url = `/parking-slots/${id}/status`;
    return axiosClient.put(url, null, {
      params: { status } // Truyền status qua query param (?status=BOOKED)
    });
  }
};

export default parkingApi;